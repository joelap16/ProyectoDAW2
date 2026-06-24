package com.proyecto.api.dto.usuario;

public class RolResponseDTO {

	private Integer id;
    private String nombre;
    
	public RolResponseDTO() {
		super();
	}

	public RolResponseDTO(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
