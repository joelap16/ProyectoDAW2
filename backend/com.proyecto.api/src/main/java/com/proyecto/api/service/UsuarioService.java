package com.proyecto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.api.dto.usuario.UsuarioCreateDTO;
import com.proyecto.api.dto.usuario.UsuarioResponseDTO;
import com.proyecto.api.enums.Role;
import com.proyecto.api.model.RolUsuario;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.model.Usuario;
import com.proyecto.api.repository.RolUsuarioRepository;
import com.proyecto.api.repository.TecnicoRepository;
import com.proyecto.api.repository.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepositorio repos;
	
	@Autowired
	TecnicoRepository reposTecnico;
	
	@Autowired
	RolUsuarioRepository reposRolUsuario;
	
	// LISTAR
	//
	
	// OLD LISTAR
	/*
	 * 
	public List<Usuario> listarUsuarios(){
		return repos.findAll();
	}
		
	*/
	
	
	// NEW LISTAR
	
	public List<UsuarioResponseDTO> listarUsuarios() {
	    return repos.findAll()
	            .stream()
	            .map(this::convertirADTO)
	            .toList();
	}
	
	// CREAR
	//
	
	// OLD CREAR
	/*
	public Usuario crearUsuario(Usuario usuario) {
		// Obtener rol de usuario
        RolUsuario rolUsuario = usuario.getRol();
        
        // Verificar si el rol es TECNICO
        if (rolUsuario != null && rolUsuario.getNombreRol() == Role.TECNICO) {
            // Si es Técnico, crear un técnico en la BD
            Tecnico tecnico = new Tecnico();
            tecnico.setUsuario(usuario);
            tecnico.setNombreTecnico(usuario.getNomUsuario());
            tecnico.setApellidoTecnico(usuario.getApeUsuario());
            tecnico.setEmailTecnico(usuario.getEmaUsuario());
            reposTecnico.save(tecnico); // Guarda el tecnico en tbl_tecnico
        } else {
            // Sino, guarda el usuario
            usuario = repos.save(usuario);
        }

        return usuario;
	}
	*/
	
	// NEW CREAR
	
	public UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto) {
		
		// VALIDAR ROL
	    RolUsuario rol = reposRolUsuario.findById(dto.getRolId())
	            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
	    
	    // VALIDAR CORREO
	    if(repos.existsByEmaUsuario(dto.getEmail())) {
	        throw new RuntimeException("Ya existe un usuario con ese correo");
	    }

	    Usuario usuario = new Usuario();
	    usuario.setNomUsuario(dto.getNombre());
	    usuario.setApeUsuario(dto.getApellido());
	    usuario.setEmaUsuario(dto.getEmail());
	    usuario.setRol(rol);

	    Usuario usuarioGuardado = repos.save(usuario);

	    // Si el usuario es técnico, crear el registro en tbl_tecnico
	    if (rol.getNombreRol() == Role.TECNICO) {
	        Tecnico tecnico = new Tecnico();
	        tecnico.setUsuario(usuarioGuardado);
	        tecnico.setNombreTecnico(usuarioGuardado.getNomUsuario());
	        tecnico.setApellidoTecnico(usuarioGuardado.getApeUsuario());
	        tecnico.setEmailTecnico(usuarioGuardado.getEmaUsuario());

	        reposTecnico.save(tecnico);
	    }

	    return convertirADTO(usuarioGuardado);
	}
	
	// ELIMINAR
	//	
	
	public void eliminarUsuario(Integer id) {
	    if (!repos.existsById(id)) {
	        throw new RuntimeException("Usuario no encontrado con id: " + id);
	    }
	    repos.deleteById(id);
	}
	
	// EDITAR
	// 
	
	public UsuarioResponseDTO editarUsuario(UsuarioCreateDTO dto, Integer id) {
	    Usuario usuario = repos.findById(id)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el id: " + id));

	    RolUsuario rol = reposRolUsuario.findById(dto.getRolId())
	            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

	    usuario.setNomUsuario(dto.getNombre());
	    usuario.setApeUsuario(dto.getApellido());
	    usuario.setEmaUsuario(dto.getEmail());
	    usuario.setRol(rol);

	    Usuario usuarioActualizado = repos.save(usuario);

	    return convertirADTO(usuarioActualizado);
	}
	
	// DTO
	//
	
	private UsuarioResponseDTO convertirADTO(Usuario usuario) {
	    UsuarioResponseDTO dto = new UsuarioResponseDTO();

	    dto.setId(usuario.getIdUsuario());
	    dto.setNombreCompleto(usuario.getNomUsuario() + " " + usuario.getApeUsuario());
	    dto.setEmail(usuario.getEmaUsuario());

	    if (usuario.getRol() != null) {
	        dto.setRol(usuario.getRol().getNombreRol().name());
	    }

	    return dto;
	}
	
	
	
}
