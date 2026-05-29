package com.proyecto.api.dto;

import java.time.LocalDateTime;

public class TicketResponseDTO {

    private Integer id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String categoria;
    private String tecnico;
    private String usuario;
    private LocalDateTime fechaCreacion;

    public TicketResponseDTO() {}

    public TicketResponseDTO(Integer id, String titulo, String descripcion, String estado, String categoria,
			String tecnico, String usuario, LocalDateTime fechaCreacion) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.categoria = categoria;
		this.tecnico = tecnico;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}