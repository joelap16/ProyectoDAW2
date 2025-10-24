package cibertec.pe.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    private String name;

    @Email(message = "Debe ingresar un correo electrónico válido.")
    @NotBlank(message = "El correo es obligatorio.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;

    @NotBlank(message = "El rol es obligatorio.")
    private String rol;
}