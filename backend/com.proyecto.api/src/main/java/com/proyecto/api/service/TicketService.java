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
	
	// LISTAR TICKETS OLD
	/* 
	public List<Ticket> listarTickets(){
		return reposTicket.findAll();
	}
	*/
	
	public List<TicketResponseDTO> listarTickets() {
	    return reposTicket.findAll()
	            .stream()
	            .map(this::convertirADTO)
	            .toList();
	}
	
	public List<Ticket> listarTicketsPorCategoria(CategoriasEnum nombreCategoria){
		return reposTicket.findByCategoria_NombreCategoria(nombreCategoria);
	}
	
	public List<Ticket> listarTicketsPorEstado(EstadosTicket estado){
		return reposTicket.findByEstado_NombreEstado(estado);
	}
	
	public Optional<Ticket> obtenerTicketPorId(int idTicket) {
	    return reposTicket.findById(idTicket);
	}
	
	//
	// CREAR
	
	public Ticket crearTicket(Ticket ticket) {
		return reposTicket.save(ticket);
	}
	
	// CREAR TICKET OLD
	/*
	public Ticket crearTicketConTecnico(Ticket ticket) {
	    Categoria categoria = ticket.getCategoria();

	    // Buscar técnicos que atienden esa categoría
	    List<Tecnico> tecnicos = reposTecnico.findByCategoria(categoria);

	    if (tecnicos.isEmpty()) {
	        throw new RuntimeException("No hay técnicos disponibles para la categoría: " + categoria.getNombreCategoria());
	    }

	    // Por ahora asignamos el primero. Puedes mejorar esto con lógica de balanceo.
	    Tecnico tecnicoAsignado = tecnicos.get(new Random().nextInt(tecnicos.size()));

	    ticket.setTecnico(tecnicoAsignado);
	    ticket.setFechaCreacion(LocalDateTime.now());

	    return reposTicket.save(ticket);
	}
	
	*/
	
	// NEW CREAR
	
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
		if (!reposTicket.existsById(idTicket)) {
	        throw new RuntimeException("El ticket no existe");
	    }
		reposTicket.deleteById(idTicket);
	}
	
	// ASIGNAR A TECNICO
	
	public Ticket asignarATecnico(int idTicket, int idTecnico) {
		Ticket ticket = reposTicket.findById(idTicket).orElse(null);
        Tecnico tecnico = reposTecnico.findById(idTecnico).orElse(null);
        if (ticket != null && tecnico != null) {
        	ticket.setTecnico(tecnico);
        	return reposTicket.save(ticket);
        }
        return null;
	}
			 
	public Ticket asignarTecnicoAutomaticamente(int idTicket) {
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
	    return reposTicket.save(ticket);
	}

	
	//
	// ACTUALIZAR ESTADOS
	
	public Ticket actualizarEstadoTicket(int idTicket, EstadosTicket nuevoEstado) {
	    Ticket ticket = reposTicket.findById(idTicket)
	        .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

	    // Buscar la instancia correcta del estado
	    EstadoTicket estado = reposEstadoTicket.findByNombreEstado(nuevoEstado)
	        .orElseThrow(() -> new RuntimeException("EstadoTicket no encontrado: " + nuevoEstado));

	    ticket.setEstado(estado);
	    return reposTicket.save(ticket);
	}
	
	//
	// ************ ATENDER TICKET *************
	
	public Ticket atenderTicket(int idTicket, EstadosTicket nuevoEstado, String comentario) {
	    Optional<Ticket> optionalTicket = reposTicket.findById(idTicket);

	    if (optionalTicket.isEmpty()) {
	        throw new RuntimeException("El ticket con ID " + idTicket + " no existe.");
	    }

	    Ticket ticket = optionalTicket.get();

	    if (nuevoEstado == EstadosTicket.ABIERTO) {
	        throw new RuntimeException("No se puede volver a poner el ticket en estado ABIERTO.");
	    }

	    if (comentario == null || comentario.trim().isEmpty()) {
	        throw new RuntimeException("Debe proporcionar un comentario para atender el ticket.");
	    }

	    // Buscar la entidad EstadoTicket correspondiente al enum
	    EstadoTicket estadoEntidad = reposEstadoTicket.findByNombreEstado(nuevoEstado)
	        .orElseThrow(() -> new RuntimeException("EstadoTicket no encontrado para: " + nuevoEstado));

	    // Asignar estado y comentario
	    ticket.setEstado(estadoEntidad);
	    ticket.setComentarios(comentario);

	    return reposTicket.save(ticket);
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
	
}
