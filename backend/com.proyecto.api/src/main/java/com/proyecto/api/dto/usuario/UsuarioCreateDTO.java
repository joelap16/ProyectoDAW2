package com.proyecto.api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDTO {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 50)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50)
    private String apellido;
    
    // (regexp = "^[.a-zA-Z0-9_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",  FORMATO CORREO
    
    @Email(message = "Formato de correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotNull(message = "El rol es obligatorio")
    private Integer rolId;
    
	public UsuarioCreateDTO() {
		super();
	}

	public UsuarioCreateDTO(String nombre, String apellido, String email, Integer rolId) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.rolId = rolId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRolId() {
		return rolId;
	}

	public void setRolId(Integer rolId) {
		this.rolId = rolId;
	}
    
}