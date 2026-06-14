package com.proyecto.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.enums.Role;
import com.proyecto.api.model.Categoria;
import com.proyecto.api.model.EstadoTicket;
import com.proyecto.api.model.RolUsuario;
import com.proyecto.api.repository.CategoriaRepository;
import com.proyecto.api.repository.EstadoTicketRepository;
import com.proyecto.api.repository.RolUsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatos(
            CategoriaRepository categoriaRepository,
            EstadoTicketRepository estadoTicketRepository,
            RolUsuarioRepository rolUsuarioRepository) {

        return args -> {

            // Categorías
            for (CategoriasEnum categoriaEnum : CategoriasEnum.values()) {

                if (!categoriaRepository.existsByNombreCategoria(categoriaEnum)) {

                    Categoria categoria = new Categoria();
                    categoria.setNombreCategoria(categoriaEnum);

                    categoriaRepository.save(categoria);
                }
            }

            // Estados
            for (EstadosTicket estadoEnum : EstadosTicket.values()) {

                if (!estadoTicketRepository.existsByNombreEstado(estadoEnum)) {

                    EstadoTicket estado = new EstadoTicket();
                    estado.setNombreEstado(estadoEnum);

                    estadoTicketRepository.save(estado);
                }
            }

            // Roles
            for (Role role : Role.values()) {

                if (!rolUsuarioRepository.existsByNombreRol(role)) {

                    RolUsuario rol = new RolUsuario();
                    rol.setNombreRol(role);

                    rolUsuarioRepository.save(rol);
                }
            }
        };
    }

}