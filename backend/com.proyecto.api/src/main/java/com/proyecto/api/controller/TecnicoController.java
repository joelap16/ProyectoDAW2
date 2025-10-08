package com.proyecto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Ticket;
import com.proyecto.api.service.TicketService;

@RestController
@RequestMapping("/api/tecnico")
public class TecnicoController {
	
	@Autowired
	private TicketService ticketService;

	// GET -- LISTAR TICKETS POR CATEGORIA

	@GetMapping("/listarPorCategoria/{nombreCategoria}")
	public ResponseEntity<List<Ticket>> listarTicketsPorCategoria(@PathVariable String nombreCategoria) {
		try {
			CategoriasEnum categoriaEnum = CategoriasEnum.valueOf(nombreCategoria.toUpperCase());
			List<Ticket> tickets = ticketService.listarTicketsPorCategoria(categoriaEnum);
			return ResponseEntity.ok(tickets);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// GET - LISTAR TICKETS POR ESTADO

	@GetMapping("/listarPorEstado/{estado}")
	public ResponseEntity<List<Ticket>> listarTicketsPorEstado(@PathVariable String estado) {
		try {
			EstadosTicket estadoEnum = EstadosTicket.valueOf(estado.toUpperCase());
			List<Ticket> tickets = ticketService.listarTicketsPorEstado(estadoEnum);
			return ResponseEntity.ok(tickets);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build(); // estado inválido
		}
	}

	// GET - OBTENER TICKET POR ID

	@GetMapping("/obtenerTicketPorId/{idTicket}")
	public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable int idTicket) {
		return ticketService.obtenerTicketPorId(idTicket).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// PUT - ATENDER TICKET

	@PutMapping("/{idTicket}/atenderTicket")
	public ResponseEntity<Ticket> atenderTicket(
	        @PathVariable int idTicket,
	        @RequestParam EstadosTicket nuevoEstado,
	        @RequestParam String comentario) {

	    if (nuevoEstado == EstadosTicket.ABIERTO) {
	        return ResponseEntity.badRequest().body(null); // no se permite volver a ABIERTO
	    }

	    try {
	        Ticket ticket = ticketService.atenderTicket(idTicket, nuevoEstado, comentario);
	        return ResponseEntity.ok(ticket);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	}
	
	
}
