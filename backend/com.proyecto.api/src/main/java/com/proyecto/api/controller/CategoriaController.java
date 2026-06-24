package com.proyecto.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.dto.categoria.CategoriaResponseDTO;
import com.proyecto.api.enums.CategoriasEnum;
import com.proyecto.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/api")
public class CategoriaController {
		
	@Autowired
	CategoriaRepository reposCategoria;

    @GetMapping("/categorias")
    public List<CategoriasEnum> listarCategorias() {

        return Arrays.asList(CategoriasEnum.values());

    }
    
    // para tecnicos-admin 
    @GetMapping("/categorias/detalle")
    public List<CategoriaResponseDTO> listarCategoriasDetalle() {

        return reposCategoria.findAll()
                .stream()
                .map(c -> new CategoriaResponseDTO(
                        c.getIdCategoria(),
                        c.getNombreCategoria().name()
                ))
                .toList();

    }

}