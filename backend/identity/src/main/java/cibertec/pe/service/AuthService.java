package cibertec.pe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cibertec.pe.dto.RegisterRequest;
import cibertec.pe.model.UsuarioCredential;
import cibertec.pe.enums.Role;
import cibertec.pe.repository.IUsuarioCredential;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AuthService {

    @Autowired
    private IUsuarioCredential repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    
    public String register(RegisterRequest request) {
        try {
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                return "El correo ya está registrado.";
            }

            UsuarioCredential user = new UsuarioCredential();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRol(Role.valueOf(request.getRol().toUpperCase()));

            repository.save(user);

            return "Usuario registrado exitosamente.";
        } catch (IllegalArgumentException e) {
            log.error("Rol inválido: {}", request.getRol());
            return "Error: Rol inválido.";
        } catch (Exception e) {
            log.error("Error al registrar usuario: {}", e.getMessage());
            return "Error al registrar usuario.";
        }
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
