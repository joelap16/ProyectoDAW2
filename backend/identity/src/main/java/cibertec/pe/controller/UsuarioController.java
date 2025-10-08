package cibertec.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cibertec.pe.dto.AuthRequest;
import cibertec.pe.model.UsuarioCredential;
import cibertec.pe.service.AuthService;

@RequestMapping("/auth")
@RestController
public class UsuarioController {
	
	@Autowired
	private AuthService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	@PostMapping("/register")
	public String addNewUser(@RequestBody UsuarioCredential user) {
		return service.saveUser(user);
	}
	
	@PostMapping("/token")
	public String getToken(@RequestBody AuthRequest authrequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		if(authentication.isAuthenticated()) {
			return service.generateToken(authrequest.getUsername());
		}
		else {
			throw new RuntimeException("Acceso Invalido");
		}
	}
	
	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return "Token Validado";
	}
}
