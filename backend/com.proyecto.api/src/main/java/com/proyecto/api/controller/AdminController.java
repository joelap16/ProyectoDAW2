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

import com.proyecto.api.enums.EstadosTicket;
import com.proyecto.api.model.Tecnico;
import com.proyecto.api.model.Ticket;
import com.proyecto.api.model.Usuario;
import com.proyecto.api.service.TecnicoService;
import com.proyecto.api.service.TicketService;
import com.proyecto.api.service.UsuarioService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TecnicoService tecnicoService;
	
	@Autowired
	TicketService ticketService;
	
	// GESTIONAR USUARIOS
	// GET - LISTAR TODOS LOS USUARIOS
	
	@GetMapping("/listarUsuarios")
	public List<Usuario> listarUsuarios(){
		return usuarioService.listarUsuarios();	
	}
	
	// GET - LISTAR TODOS LOS TECNICOS
	
		@GetMapping("/listarTecnicos")
		public List<Tecnico> listarTecnicos(){
			return tecnicoService.listarTecnicos();	
		}
		
	// POST - CREAR USUARIO
	
	@PostMapping("/crearUsuario")
	public Usuario crearUsuario(@RequestBody Usuario usuario) {
		return usuarioService.crearUsuario(usuario);
	}
	
	// PUT - EDITAR USUARIO POR SU ID
	
	@PutMapping("/editarUsuario/{id}")
	public ResponseEntity<String> editarUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
	    String mensaje = usuarioService.editarUsuario(usuario, id);
	    return ResponseEntity.ok(mensaje);
	}
	
	// DELETE - ELIMINAR USUARIO POR SU ID
	
	@DeleteMapping("/eliminarUsuario/{id}")
	public void eliminarUsuario(@PathVariable int id) {
		usuarioService.eliminarUsuario(id);
	}
	
	// ASIGNAR CATEGORIA DE TECNICOS	
	// EL IDCATEGORIA SE ENVIA SOLO COMO PARAMETRO
	
    @PutMapping("/{id}/asignarCategoriaATecnico")
    public ResponseEntity<?> asignarCategoria(
        @PathVariable int id,
        @RequestParam int categoriaId) {

        tecnicoService.asignarCategoriaATecnico(id, categoriaId);
        return ResponseEntity.ok("Categoría asignada con éxito");
    }
    
    // GESTIONAR TICKETS
    // GET -- LISTAR TODOS LOS TICKETS
	
 	@GetMapping("/listarTickets")
 	public List<Ticket> listarTickets(){
 		return ticketService.listarTickets();
 	}
    
 	// PUT - ASIGNAR TECNICO A UN TICKET MANUALMENTE
	
 	@PutMapping("/{idTicket}/asignar-tecnico/{idTecnico}")
 	public ResponseEntity<Ticket> asignarATecnico(@PathVariable int idTicket, @PathVariable int idTecnico) {
 	    Ticket ticket = ticketService.asignarATecnico(idTicket, idTecnico);
 	    return (ticket != null) ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
 	}
 	
 	// PUT - ASIGNAR ESTADO DE TICKET
 	
 	@PutMapping("/{idTicket}/actualizarEstado")
 	public ResponseEntity<Ticket> actualizarEstadoTicket(
 	        @PathVariable int idTicket,
 	        @RequestParam EstadosTicket nuevoEstado) {
 	    try {
 	        Ticket ticket = ticketService.actualizarEstadoTicket(idTicket, nuevoEstado);
 	        return ResponseEntity.ok(ticket);
 	    } catch (RuntimeException e) {
 	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
 	    }
 	}

 	
 	// DELETE - ELIMINAR UN TICKET POR SU ID
	
 	@DeleteMapping("/eliminarTicket/{id}")
 	public ResponseEntity<String> eliminarTicket(@PathVariable("id") int idTicket) {
 	    ticketService.eliminarTicketPorId(idTicket);
 	    return ResponseEntity.ok("Ticket eliminado correctamente");
 	}
}
