package com.proyecto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.model.Ticket;
import com.proyecto.api.service.TicketService;
import com.proyecto.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TicketService ticketService;
	
	// POST - CREAR TICKET Y ASIGNAR TECNICO AUTOMATICAMENTE SEGUN CATEGORIA DE TICKET
	
	@PostMapping("/crearTicket")
	public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
		Ticket nuevoTicket = ticketService.crearTicketConTecnico(ticket);
		return new ResponseEntity<>(nuevoTicket, HttpStatus.CREATED);
	}

	// GET - OBTENER TICKET POR ID

	@GetMapping("/{idTicket}")
	public ResponseEntity<Ticket> obtenerTicketPorId(@PathVariable int idTicket) {
		return ticketService.obtenerTicketPorId(idTicket).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

}
