package com.proyecto.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.api.enums.Role;
import com.proyecto.api.model.RolUsuario;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.model.Usuario;
import com.proyecto.api.repository.TecnicoRepository;
import com.proyecto.api.repository.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepositorio repos;
	
	@Autowired
	TecnicoRepository reposTecnico;
	
	public List<Usuario> listarUsuarios(){
		return repos.findAll();
	}
	
	public Usuario crearUsuario(Usuario usuario) {
		// Obtener el rol de usuario que será asignado
        RolUsuario rolUsuario = usuario.getRol();
        
        // Verificar si el rol es TECNICO
        if (rolUsuario != null && rolUsuario.getNombreRol() == Role.TECNICO) {
            // Si es Técnico, crear un técnico en la base de datos
            Tecnico tecnico = new Tecnico();
            tecnico.setUsuario(usuario);
            tecnico.setNombreTecnico(usuario.getNomUsuario());
            tecnico.setApellidoTecnico(usuario.getApeUsuario());
            tecnico.setEmailTecnico(usuario.getEmaUsuario());
            reposTecnico.save(tecnico); // Guardar el técnico en la tabla correspondiente
        } else {
            // Si no es técnico, solo guardar el usuario
            usuario = repos.save(usuario);
        }

        return usuario;
	}
	
	public void eliminarUsuario(Integer id) {
		repos.deleteById(id);
	}
	
	public String editarUsuario(Usuario usuario, Integer id) {
		Usuario usuario1 = repos.findById(id).get();
		if (usuario1 != null) {
			usuario1.setNomUsuario(usuario.getNomUsuario());
			usuario1.setApeUsuario(usuario.getApeUsuario());
			usuario1.setEmaUsuario(usuario.getEmaUsuario());
			usuario1.setRol(usuario.getRol());
			
			repos.save(usuario1);
			return "Usuario actualizado correctamente";
		}
		else
			throw new RuntimeException("Usuario no encontrado con el id: "+ id);
	}
}
