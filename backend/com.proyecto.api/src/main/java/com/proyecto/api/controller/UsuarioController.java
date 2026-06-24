package com.proyecto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.api.dto.ticket.TicketCreateDTO;
import com.proyecto.api.dto.ticket.TicketResponseDTO;
import com.proyecto.api.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private TicketService ticketService;

    // CREAR TICKET
    @PostMapping("/tickets")    
    public ResponseEntity<TicketResponseDTO> crearTicket(
            @RequestHeader("X-User-Email") String email,
            @Valid @RequestBody TicketCreateDTO dto) {

        TicketResponseDTO ticketCreado =
                ticketService.crearTicket(email, dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketCreado);
    }

    // LISTAR MIS TICKETS
    @GetMapping("/tickets/mis")    
    public ResponseEntity<List<TicketResponseDTO>> listarMisTickets(
            @RequestHeader("X-User-Email") String email) {

        return ResponseEntity.ok(
                ticketService.listarTicketsDelUsuarioAutenticado(email)
        );
    }

    // OBTENER TICKET POR ID SOLO SI ES DEL USUARIO AUTENTICADO
    @GetMapping("/tickets/{idTicket}")
    public ResponseEntity<TicketResponseDTO> obtenerTicketPorId(
            @RequestHeader("X-User-Email") String email,
            @PathVariable Integer idTicket) {

        return ResponseEntity.ok(
                ticketService.obtenerTicketDelUsuarioAutenticado(
                        email,
                        idTicket
                )
        );
    }
}