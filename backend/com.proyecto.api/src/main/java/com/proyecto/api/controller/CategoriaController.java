package com.proyecto.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.enums.CategoriasEnum;

@RestController
@RequestMapping("/api")
public class CategoriaController {

    @GetMapping("/categorias")
    public List<CategoriasEnum> listarCategorias() {

        return Arrays.asList(CategoriasEnum.values());

    }

}