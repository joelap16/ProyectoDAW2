package com.proyecto.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
