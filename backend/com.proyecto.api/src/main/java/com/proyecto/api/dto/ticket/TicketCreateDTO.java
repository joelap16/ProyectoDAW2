package com.proyecto.api.dto.ticket;

public class TicketCreateDTO {

    private String titulo;
    private String descripcion;
    private Integer categoriaId;
    private Integer usuarioId;

    public TicketCreateDTO() {}
    
    public TicketCreateDTO(String titulo, String descripcion, Integer categoriaId, Integer usuarioId) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.categoriaId = categoriaId;
		this.usuarioId = usuarioId;
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

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}