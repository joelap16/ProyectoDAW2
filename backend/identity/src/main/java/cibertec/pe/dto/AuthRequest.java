package cibertec.pe.dto;

import jakarta.validation.constraints.*;

public class AuthRequest {
	
	@NotBlank(message = "El nombre de usuario es obligatorio")
	@Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
	private String username;
	
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 6, message = "La contraseña debe contener almenos 6 caracteres")
	private String password;
	
	public AuthRequest() {
		super();
	}
	public AuthRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
