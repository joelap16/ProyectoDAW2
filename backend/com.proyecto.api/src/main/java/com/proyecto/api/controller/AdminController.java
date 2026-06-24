package com.proyecto.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.api.dto.tecnico.TecnicoResponseDTO;
import com.proyecto.api.dto.ticket.TicketResponseDTO;
import com.proyecto.api.dto.usuario.RolResponseDTO;
import com.proyecto.api.dto.usuario.UsuarioCreateDTO;
import com.proyecto.api.dto.usuario.UsuarioResponseDTO;
import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.model.Ticket;
import com.proyecto.api.model.Usuario;
import com.proyecto.api.repository.RolUsuarioRepository;
import com.proyecto.api.service.TecnicoService;
import com.proyecto.api.service.TicketService;
import com.proyecto.api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TecnicoService tecnicoService;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	RolUsuarioRepository reposRolUsuario;
	
	// GESTIONAR USUARIOS
	// GET - LISTAR TODOS LOS USUARIOS
	
	@GetMapping("/usuarios")
	public List<UsuarioResponseDTO> listarUsuarios(){
		return usuarioService.listarUsuarios();	
	}
	
	// GET - LISTAR TECNICOS
	
	
	@GetMapping("/tecnicos")
	public List<TecnicoResponseDTO> listarTecnicos(){
	    return tecnicoService.listarTecnicos();
	}
		
	// POST - CREAR USUARIO
	
	@PostMapping("/usuarios")
	public ResponseEntity<UsuarioResponseDTO> crearUsuario(
	        @Valid @RequestBody UsuarioCreateDTO dto) {

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(usuarioService.crearUsuario(dto));
	}
	
	// PUT - EDITAR USUARIO POR SU ID
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<UsuarioResponseDTO> editarUsuario(
			@Valid
			@RequestBody UsuarioCreateDTO dto, @PathVariable Integer id) {
	    UsuarioResponseDTO usuarioActualizado = usuarioService.editarUsuario(dto, id);
	    return ResponseEntity.ok(usuarioActualizado);
	}
	
	// DELETE - ELIMINAR USUARIO POR SU ID
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
	    usuarioService.eliminarUsuario(id);
	    return ResponseEntity.ok("Usuario eliminado correctamente");
	}
	
	// ASIGNAR CATEGORIA DE TECNICOS	
	// EL IDCATEGORIA SE ENVIA SOLO COMO PARAMETRO
	
		@PutMapping("/tecnicos/{id}/categoria")
	public ResponseEntity<TecnicoResponseDTO> asignarCategoria(
	        @PathVariable Integer id,
	        @RequestParam Integer categoriaId) {

	    return ResponseEntity.ok(
	            tecnicoService.asignarCategoriaATecnico(
	                    id,
	                    categoriaId));
	}
    
    // GESTIONAR TICKETS
    // GET -- LISTAR TODOS LOS TICKETS
	
 	@GetMapping("/tickets")
 	public List<TicketResponseDTO> listarTickets(){
 		return ticketService.listarTickets();
 	}
    
 	// PUT - ASIGNAR TECNICO A UN TICKET MANUALMENTE
	
 	@PutMapping("/tickets/{idTicket}/tecnico/{idTecnico}")
 	public ResponseEntity<TicketResponseDTO> asignarATecnico(
 	        @PathVariable Integer idTicket,
 	        @PathVariable Integer idTecnico) {

 	    return ResponseEntity.ok(
 	            ticketService.asignarATecnico(idTicket, idTecnico));
 	}
 	
 	// PUT - ASIGNAR ESTADO DE TICKET
 	
 	@PutMapping("/tickets/{idTicket}/estado")
 	public ResponseEntity<TicketResponseDTO> actualizarEstadoTicket(
 	        @PathVariable Integer idTicket,
 	        @RequestParam EstadosTicket nuevoEstado) {

 	    return ResponseEntity.ok(
 	            ticketService.actualizarEstadoTicket(
 	                    idTicket,
 	                    nuevoEstado));
 	}
 	
 	// DELETE - ELIMINAR UN TICKET POR SU ID 
 	
 	// NO IMPLEMENTADO EN USUARIOS, PARA FUTURA MEJORA
	
 	@DeleteMapping("/tickets/{id}")
 	public ResponseEntity<String> eliminarTicket(@PathVariable("id") int idTicket) {
 	    ticketService.eliminarTicketPorId(idTicket);
 	    return ResponseEntity.ok("Ticket eliminado correctamente");
 	}
 	
 	// ROLES
 	
 	@GetMapping("/roles")
 	public List<RolResponseDTO> listarRoles() {
 	    return reposRolUsuario.findAll()
 	        .stream()
 	        .map(r -> new RolResponseDTO(r.getIdRol(), r.getNombreRol().name()))
 	        .toList();
 	}
}
