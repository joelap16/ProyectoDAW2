package com.proyecto.api.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Categoria;
import com.proyecto.api.model.EstadoTicket;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.model.Ticket;
import com.proyecto.api.repository.EstadoTicketRepository;
import com.proyecto.api.repository.TecnicoRepository;
import com.proyecto.api.repository.TicketRepository;

@Service
public class TicketService {

	// REPOSITORIOS
	
	@Autowired
	TicketRepository reposTicket;
	
	@Autowired
	TecnicoRepository reposTecnico;
	
	@Autowired
	EstadoTicketRepository reposEstadoTicket;
	
	// LISTAR
	
	public List<Ticket> listarTickets(){
		return reposTicket.findAll();
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
	// ATENDER TICKET
	
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
	
}
