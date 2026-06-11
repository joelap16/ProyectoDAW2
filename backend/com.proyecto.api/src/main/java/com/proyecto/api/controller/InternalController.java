package com.proyecto.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.dto.usuario.UsuarioCreateDTO;
import com.proyecto.api.dto.usuario.UsuarioResponseDTO;
import com.proyecto.api.service.UsuarioService;

@RestController
@RequestMapping("/api/internal")
public class InternalController {
	
	@Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> crearUsuarioInterno(
            @RequestBody UsuarioCreateDTO dto) {

        return ResponseEntity.ok(
                usuarioService.crearUsuario(dto));
    }

}
