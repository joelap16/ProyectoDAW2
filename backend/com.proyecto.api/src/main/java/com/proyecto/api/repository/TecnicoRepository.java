package com.proyecto.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.model.Categoria;
import com.proyecto.api.model.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    List<Tecnico> findByCategoria(Categoria categoria);

    List<Tecnico> findByCategoria_NombreCategoria(CategoriasEnum nombreCategoria);

    Optional<Tecnico> findByUsuario_EmaUsuario(String emaUsuario);
}