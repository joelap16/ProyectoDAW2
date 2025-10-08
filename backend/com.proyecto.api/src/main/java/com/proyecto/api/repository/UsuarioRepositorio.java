package com.proyecto.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.api.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
	
}
