package com.proyecto.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByCategoria_NombreCategoria(CategoriasEnum nombreCategoria);

    List<Ticket> findByEstado_NombreEstado(EstadosTicket estado);

    List<Ticket> findByTecnico_Id(Integer idTecnico);

    Optional<Ticket> findByIdTicketAndTecnico_Id(Integer idTicket, Integer idTecnico);

    Optional<Ticket> findByIdTicketAndUsuario_IdUsuario(Integer idTicket, Integer idUsuario);

    List<Ticket> findByUsuario_IdUsuario(Integer idUsuario);

    int countByTecnico_Id(Integer idTecnico);

    boolean existsByUsuario_IdUsuario(Integer idUsuario);

    boolean existsByTecnico_Id(Integer idTecnico);
}