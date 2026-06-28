package cibertec.pe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cibertec.pe.dto.RegisterRequest;
import cibertec.pe.repository.IUsuarioCredential;
import cibertec.pe.service.AuthService;

@Component
public class InitializerConfig implements CommandLineRunner {

    @Autowired
    private AuthService authService;

    @Autowired
    private IUsuarioCredential credentialRepository;

    @Override
    public void run(String... args) throws Exception {

        crearSiNoExiste(
            "admin@gmail.com",
            "Admin",
            "Sistema",
            "admin123",
            "ADMINISTRADOR"
        );

        crearSiNoExiste(
            "soporte@gmail.com",
            "Soporte",
            "Tecnico",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "software@gmail.com",
            "Software",
            "Tecnico",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "hardware@gmail.com",
            "Hardware",
            "Tecnico",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "bd@gmail.com",
            "Base",
            "Datos",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "mantenimiento@gmail.com",
            "Mantenimiento",
            "Tecnico",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "acceso@gmail.com",
            "Acceso",
            "Cuentas",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "incidencia@gmail.com",
            "Incidencia",
            "Tecnico",
            "tecnico123",
            "TECNICO"
        );

        crearSiNoExiste(
            "impresoras@gmail.com",
            "Impresoras",
            "Tecnico",
            "tecnico123",
            "TECNICO"
        );
    }

    private void crearSiNoExiste(
            String email,
            String name,
            String apellido,
            String password,
            String rol) {

        if (!credentialRepository.existsByEmail(email)) {

            RegisterRequest request = new RegisterRequest();
            request.setName(name);
            request.setApellido(apellido);
            request.setEmail(email);
            request.setPassword(password);
            request.setRol(rol);

            authService.register(request);
        }
    }
}