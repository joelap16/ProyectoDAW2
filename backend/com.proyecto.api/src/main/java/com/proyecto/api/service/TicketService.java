package com.proyecto.api.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.api.dto.ticket.TicketCreateDTO;
import com.proyecto.api.dto.ticket.TicketResponseDTO;
import com.proyecto.api.dto.ticket.TicketUpdateDTO;
import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Categoria;
import com.proyecto.api.model.EstadoTicket;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.model.Ticket;
import com.proyecto.api.model.Usuario;
import com.proyecto.api.repository.CategoriaRepository;
import com.proyecto.api.repository.EstadoTicketRepository;
import com.proyecto.api.repository.TecnicoRepository;
import com.proyecto.api.repository.TicketRepository;
import com.proyecto.api.repository.UsuarioRepositorio;

@Service
public class TicketService {

	// REPOSITORIOS
	
	@Autowired
	TicketRepository reposTicket;
	
	@Autowired
	TecnicoRepository reposTecnico;
	
	@Autowired
	EstadoTicketRepository reposEstadoTicket;
	
	@Autowired
	CategoriaRepository reposCategoria;
	
	@Autowired
	UsuarioRepositorio reposUsuario;
	
	// LISTAR
			
	public List<TicketResponseDTO> listarTickets() {
	    return reposTicket.findAll()
	            .stream()
	            .map(this::convertirADTO)
	            .toList();
	}
	
		
	// NEW LISTAR POR CATEGORIA
	//
	public List<TicketResponseDTO> listarTicketsPorCategoria(
	        CategoriasEnum nombreCategoria) {

	    return reposTicket.findByCategoria_NombreCategoria(nombreCategoria)
	            .stream()
	            .map(this::convertirADTO)
	            .toList();
	}
	
	// NEW LISTAR POR ESTADO
	
	public List<TicketResponseDTO> listarTicketsPorEstado(
	        EstadosTicket estado) {

	    return reposTicket.findByEstado_NombreEstado(estado)
	            .stream()
	            .map(this::convertirADTO)
	            .toList();
	}
		
	// CREAR
	
	public TicketResponseDTO crearTicket(TicketCreateDTO dto) {
	    Usuario usuario = reposUsuario.findById(dto.getUsuarioId())
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    Categoria categoria = reposCategoria.findById(dto.getCategoriaId())
	            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

	    EstadoTicket estadoInicial = reposEstadoTicket.findByNombreEstado(EstadosTicket.ABIERTO)
	            .orElseThrow(() -> new RuntimeException("Estado ABIERTO no existe"));

	    Ticket ticket = new Ticket();
	    ticket.setTitulo(dto.getTitulo());
	    ticket.setDescripcion(dto.getDescripcion());
	    ticket.setUsuario(usuario);
	    ticket.setCategoria(categoria);
	    ticket.setFechaCreacion(LocalDateTime.now());
	    ticket.setEstado(estadoInicial);

	    // ASIGNAR TECNICO SEGUN CATEGORIA
	    List<Tecnico> tecnicos = reposTecnico.findByCategoria(categoria);

	    if (tecnicos.isEmpty()) {
	        throw new RuntimeException("No hay técnicos disponibles para esta categoría");
	    }

	    Tecnico tecnicoAsignado = tecnicos.get(0); // luego puedes mejorar esto (RANDOM)
	    // HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA POSIBLE MEJORAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	    ticket.setTecnico(tecnicoAsignado);

	    Ticket ticketGuardado = reposTicket.save(ticket);

	    return convertirADTO(ticketGuardado);
	}
	

	//
	// ELIMINAR
	
	public void eliminarTicketPorId(int idTicket) {
		
		Ticket ticket = reposTicket.findById(idTicket)
		        .orElseThrow(() ->
		                new RuntimeException("El ticket no existe"));
		
		if (ticket.getEstado().getNombreEstado() == EstadosTicket.RESUELTO) {
		    throw new RuntimeException(
		            "No se puede eliminar un ticket finalizado");
		}

		reposTicket.delete(ticket);
	}
	
	// ASIGNAR A TECNICO
	
	public TicketResponseDTO asignarATecnico(
	        Integer idTicket,
	        Integer idTecnico) {

	    Ticket ticket = reposTicket.findById(idTicket)
	            .orElseThrow(() ->
	                    new RuntimeException("Ticket no encontrado"));

	    Tecnico tecnico = reposTecnico.findById(idTecnico)
	            .orElseThrow(() ->
	                    new RuntimeException("Técnico no encontrado"));

	    ticket.setTecnico(tecnico);

	    Ticket ticketActualizado = reposTicket.save(ticket);

	    return convertirADTO(ticketActualizado);
	}
	
	
	
	public TicketResponseDTO asignarTecnicoAutomaticamente(
	        Integer idTicket) {
	    Ticket ticket = reposTicket.findById(idTicket)
	        .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

	    CategoriasEnum categoria = ticket.getCategoria().getNombreCategoria();

	    List<Tecnico> tecnicos = reposTecnico.findByCategoria_NombreCategoria(categoria);

	    if (tecnicos.isEmpty()) {
	        throw new RuntimeException("No hay técnicos disponibles para esta categoría");
	    }

	    Tecnico tecnicoConMenosTickets = tecnicos.stream()
	        .min(Comparator.comparingInt(t -> reposTicket.countByTecnico_Id(t.getId())))
	        .orElseThrow(() -> new RuntimeException("Error al seleccionar técnico"));

	    ticket.setTecnico(tecnicoConMenosTickets);
	    Ticket ticketActualizado = reposTicket.save(ticket);

	    return convertirADTO(ticketActualizado);
	}

	
	//
	// ACTUALIZAR ESTADOS
	
	public TicketResponseDTO actualizarEstadoTicket(
	        Integer idTicket,
	        EstadosTicket nuevoEstado) {

	    Ticket ticket = reposTicket.findById(idTicket)
	            .orElseThrow(() ->
	                    new RuntimeException("Ticket no encontrado"));
	    
	    if (ticket.getEstado().getNombreEstado() == EstadosTicket.RESUELTO) {
	        throw new RuntimeException(
	                "El ticket ya está finalizado y no puede modificarse");
	    }
	    
	    if (nuevoEstado == EstadosTicket.ABIERTO) {
	        throw new RuntimeException(
	                "No se puede volver a poner el ticket en estado ABIERTO");
	    }

	    EstadoTicket estado =
	            reposEstadoTicket.findByNombreEstado(nuevoEstado)
	            .orElseThrow(() ->
	                    new RuntimeException("Estado no encontrado"));

	    ticket.setEstado(estado);

	    Ticket ticketActualizado = reposTicket.save(ticket);

	    return convertirADTO(ticketActualizado);
	}
	
	//
	// ************ ATENDER TICKET *************
	
	//
	// NEW ATENDER TICKET
	
	public TicketResponseDTO atenderTicket(
	        Integer idTicket,
	        TicketUpdateDTO dto) {

	    Ticket ticket = reposTicket.findById(idTicket)
	            .orElseThrow(() ->
	                    new RuntimeException("Ticket no encontrado"));
	    
	    if (ticket.getEstado().getNombreEstado() == EstadosTicket.RESUELTO) {
	        throw new RuntimeException(
	                "El ticket ya está finalizado y no puede modificarse");
	    }

	    if(dto.getEstado() == EstadosTicket.ABIERTO) {
	        throw new RuntimeException(
	                "No se puede volver a poner el ticket en estado ABIERTO");
	    }

	    EstadoTicket estadoEntidad =
	            reposEstadoTicket.findByNombreEstado(dto.getEstado())
	            .orElseThrow(() ->
	                    new RuntimeException("Estado no encontrado"));

	    ticket.setEstado(estadoEntidad);
	    ticket.setComentarios(dto.getComentario());
	    
	    

	    Ticket ticketActualizado = reposTicket.save(ticket);

	    return convertirADTO(ticketActualizado);
	}
	
	
	//
	// DTO - CONVERTIR TICKET A DTO
	
	private TicketResponseDTO convertirADTO(Ticket ticket) {
		
		TicketResponseDTO dto = new TicketResponseDTO();
		
		dto.setId(ticket.getIdTicket());
	    dto.setTitulo(ticket.getTitulo());
	    dto.setDescripcion(ticket.getDescripcion());
	    
	    dto.setFechaCreacion(ticket.getFechaCreacion());
	    
	    if (ticket.getEstado() != null) {
	        dto.setEstado(ticket.getEstado().getNombreEstado().name());
	    }

	    if (ticket.getCategoria() != null) {
	        dto.setCategoria(ticket.getCategoria().getNombreCategoria().name());
	    }

	    if (ticket.getUsuario() != null) {
	        dto.setUsuario(
	            ticket.getUsuario().getNomUsuario() + " " +
	            ticket.getUsuario().getApeUsuario()
	        );
	    }

	    if (ticket.getTecnico() != null) {
	        dto.setTecnico(
	            ticket.getTecnico().getNombreTecnico() + " " +
	            ticket.getTecnico().getApellidoTecnico()
	        );
	    }

	    return dto;
	}
	
	// OBTENER TICKET POR ID (DTO)
	
	public TicketResponseDTO obtenerTicketPorId(int idTicket) {
	    Ticket ticket = reposTicket.findById(idTicket)
	            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
	    return convertirADTO(ticket);
	}
	
}
