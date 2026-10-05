package cibertec.pe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.beans.factory.annotation.Value;

import cibertec.pe.dto.RegisterRequest;
import cibertec.pe.dto.api.UsuarioApiRequest;
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

    @Autowired
    private WebClient webClient;
    
    @Value("${api.service.url}")
    private String apiServiceUrl;

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

            UsuarioApiRequest dto = new UsuarioApiRequest();

            dto.setNombre(request.getName());
            dto.setApellido(request.getApellido());
            dto.setEmail(request.getEmail());
            dto.setRolId(mapRolToRolId(user.getRol()));

            webClient.post()
            .uri(apiServiceUrl + "/api/internal/usuarios")
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(String.class)
            .block();

            return "Usuario registrado exitosamente.";
        } catch (IllegalArgumentException e) {
            log.error("Rol inválido: {}", request.getRol());
            return "Error: Rol inválido.";
        } catch (Exception e) {
            log.error("Error al registrar usuario: {}", e.getMessage());
            return "Error al registrar usuario.";
        }
    }

    private Integer mapRolToRolId(Role rol) {
        return switch (rol) {
            case ADMINISTRADOR -> 1;
            case TECNICO -> 2;
            case USUARIO -> 3;
        };
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
