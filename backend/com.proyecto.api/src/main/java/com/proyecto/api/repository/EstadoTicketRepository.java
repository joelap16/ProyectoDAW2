package com.proyecto.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.EstadoTicket;

public interface EstadoTicketRepository extends JpaRepository<EstadoTicket, Integer> {

	Optional<EstadoTicket> findByNombreEstado(EstadosTicket nombreEstado);
}
