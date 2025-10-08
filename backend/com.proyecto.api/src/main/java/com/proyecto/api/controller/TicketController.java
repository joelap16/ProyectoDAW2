package com.proyecto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.service.TecnicoService;
import com.proyecto.api.service.TicketService;

// SIN USAR
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

	@Autowired
	TicketService serviceTicket;
	
	@Autowired
	TecnicoService serviceTecnico;
		
	// PUT - ASIGNAR TECNICO AUTOMATICAMENTE POR CATEGORIA
	/*
	@PutMapping("/{idTicket}/asignar-tecnico-automatico")
	public ResponseEntity<Ticket> asignarTecnicoAutomatico(@PathVariable int idTicket) {
	    try {
	        Ticket ticket = serviceTicket.asignarTecnicoAutomaticamente(idTicket);
	        return ResponseEntity.ok(ticket);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	}
	*/
	
}
