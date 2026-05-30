package com.proyecto.api.dto.tecnico;

public class TecnicoResponseDTO {

    private Integer id;
    private String nombreCompleto;
    private String email;
    private String categoria;
    
	public TecnicoResponseDTO() {
		super();
	}

	public TecnicoResponseDTO(Integer id, String nombreCompleto, String email, String categoria) {
		super();
		this.id = id;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.categoria = categoria;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
