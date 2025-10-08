package com.proyecto.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    
	// BUSCAR TODOS LOS TICKETS DE UNA CATEGORIA ESPECIFICA (por enum)
	List<Ticket> findByCategoria_NombreCategoria(CategoriasEnum nombreCategoria);
	// BUSCAR TODOS LOS TICKETS POR UN ESTADO ESPECIFICO (EstadosTicket)
	List<Ticket> findByEstado_NombreEstado(EstadosTicket estado);
	// BUSCAR TODOS LOS TICKETS ASIGNADOS A UN TECNICO POR SU ID
	List<Ticket> findByTecnicoId(int id);
	// CUENTA LA CANTIDAD DE TICKETS ASIGNADOS A UN TECNICO (POR SU ID)
	int countByTecnico_Id(int idTecnico);
}
