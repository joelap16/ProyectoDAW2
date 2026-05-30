package com.proyecto.api.dto.usuario;

public class UsuarioCreateDTO {

    private String nombre;
    private String apellido;
    private String email;
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