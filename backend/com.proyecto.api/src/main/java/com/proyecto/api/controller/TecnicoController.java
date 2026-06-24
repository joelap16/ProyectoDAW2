package com.proyecto.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.api.dto.ticket.TicketResponseDTO;
import com.proyecto.api.dto.ticket.TicketUpdateDTO;
import com.proyecto.api.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tecnico")
public class TecnicoController {

    @Autowired
    private TicketService ticketService;

    // LISTAR MIS TICKETS, CON FILTROS OPCIONALES POR ESTADO Y CATEGORIA
    @GetMapping("/tickets/mis")
    public ResponseEntity<List<TicketResponseDTO>> listarMisTickets(
            Principal principal,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String categoria) {

        return ResponseEntity.ok(
                ticketService.listarTicketsDelTecnicoAutenticado(
                        principal.getName(),
                        estado,
                        categoria
                )
        );
    }

    // OBTENER DETALLE DE UN TICKET SOLO SI ESTÁ ASIGNADO AL TECNICO AUTENTICADO
    @GetMapping("/tickets/{idTicket}")
    public ResponseEntity<TicketResponseDTO> obtenerTicketPorId(
            Principal principal,
            @PathVariable Integer idTicket) {

        return ResponseEntity.ok(
                ticketService.obtenerTicketDelTecnicoAutenticado(principal.getName(), idTicket)
        );
    }

    // ATENDER / CAMBIAR ESTADO DEL TICKET
    @PutMapping("/tickets/{idTicket}/estado")
    public ResponseEntity<TicketResponseDTO> atenderTicket(
            Principal principal,
            @PathVariable Integer idTicket,
            @Valid @RequestBody TicketUpdateDTO dto) {

        return ResponseEntity.ok(
                ticketService.atenderTicketDelTecnico(principal.getName(), idTicket, dto)
        );
    }
}