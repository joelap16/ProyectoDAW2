package cibertec.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import cibertec.pe.dto.AuthRequest;
import cibertec.pe.dto.RegisterRequest;
import cibertec.pe.service.AuthService;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class UsuarioController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Registro de usuario
    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterRequest request) {
        return service.register(request);
    }

    // Login (obtencion y generacion de token)
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Acceso inválido");
        }
    }

    // Validar Token
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token validado correctamente.";
    }
}
