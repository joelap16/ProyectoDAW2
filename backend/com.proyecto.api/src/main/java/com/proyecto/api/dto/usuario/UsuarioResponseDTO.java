package com.proyecto.api.dto.usuario;

public class UsuarioResponseDTO {

    private Integer id;
    private String nombreCompleto;
    private String email;
    private String rol;
    
	public UsuarioResponseDTO() {
		super();
	}

	public UsuarioResponseDTO(Integer id, String nombreCompleto, String email, String rol) {
		super();
		this.id = id;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.rol = rol;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}    

}