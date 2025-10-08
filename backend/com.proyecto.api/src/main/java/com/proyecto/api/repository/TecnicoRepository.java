package com.proyecto.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.model.Categoria;
import com.proyecto.api.model.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	// BUSCAR TODOS LOS TECNICOS POR UNA CATEGORIA ESPECIFICA (por objeto CategoriaTecnico)
	List<Tecnico> findByCategoria(Categoria categoria);
	// BUSCAR TODOS LOS TECNICOS CUYO NOMBRE DE CATEGORIA (ENUM) COINCIDA CON EL VALOR DADO
	List<Tecnico> findByCategoria_NombreCategoria(CategoriasEnum nombreCategoria);	
}
