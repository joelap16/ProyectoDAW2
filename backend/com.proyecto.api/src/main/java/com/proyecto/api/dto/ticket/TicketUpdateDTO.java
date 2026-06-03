package com.proyecto.api.dto.ticket;

import com.proyecto.api.enums.EstadosTicket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketUpdateDTO {

    @NotNull(message = "Debe seleccionar un estado")
    private EstadosTicket estado;

    @NotBlank(message = "Debe ingresar un comentario")
    private String comentario;

    public TicketUpdateDTO() {
    }

    public TicketUpdateDTO(EstadosTicket estado, String comentario) {
        this.estado = estado;
        this.comentario = comentario;
    }

    public EstadosTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadosTicket estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}