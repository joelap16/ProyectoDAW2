package com.proyecto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.dto.ticket.TicketResponseDTO;
import com.proyecto.api.dto.ticket.TicketUpdateDTO;
import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Ticket;
import com.proyecto.api.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tecnico")
public class TecnicoController {
	
	@Autowired
	private TicketService ticketService;

	// GET -- LISTAR TICKETS POR CATEGORIA

	@GetMapping("/tickets/categoria/{categoria}")
	public ResponseEntity<List<TicketResponseDTO>> listarTicketsPorCategoria(@PathVariable String categoria) {
		CategoriasEnum categoriaEnum = CategoriasEnum.valueOf(categoria.toUpperCase());
		List<TicketResponseDTO> tickets = ticketService.listarTicketsPorCategoria(categoriaEnum);
		return ResponseEntity.ok(tickets);
	}

	// GET - LISTAR TICKETS POR ESTADO

	@GetMapping("/tickets/estado/{estado}")
	public ResponseEntity<List<TicketResponseDTO>> listarTicketsPorEstado(@PathVariable String estado) {
		EstadosTicket estadoEnum = EstadosTicket.valueOf(estado.toUpperCase());
		List<TicketResponseDTO> tickets = ticketService.listarTicketsPorEstado(estadoEnum);
		return ResponseEntity.ok(tickets);
	}

	// GET - OBTENER TICKET POR ID
	
	@GetMapping("/tickets/{idTicket}")
	public ResponseEntity<TicketResponseDTO> obtenerTicketPorId(@PathVariable int idTicket) {
	    return ResponseEntity.ok(ticketService.obtenerTicketPorId(idTicket));
	}

	// PUT - ATENDER TICKET

	@PutMapping("/tickets/{idTicket}/estado")
	public ResponseEntity<TicketResponseDTO> atenderTicket(
	        @PathVariable Integer idTicket,
	        @Valid @RequestBody TicketUpdateDTO dto) {

	    return ResponseEntity.ok(ticketService.atenderTicket(idTicket, dto));
	}
	
	
}
