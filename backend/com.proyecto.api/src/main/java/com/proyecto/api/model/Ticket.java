package com.proyecto.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTicket;
	
	@Column(name = "title_ticket")
	private String titulo;
	
	@Column(name = "desc_ticket")
	private String descripcion;
	
	@Column(name = "fech_creacion_ticket")
	private LocalDateTime fechaCreacion; 
	
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "estado_ticket")
	private EstadoTicket estado;
	
	@Column(name = "comments")
	private String comentarios = "";
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")	
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id")
	@JsonBackReference
	private Tecnico tecnico;
	
	public Ticket() {
		super();
	}
	
	public Ticket(int idTicket, String titulo, String descripcion, LocalDateTime fechaCreacion, Categoria categoria,
			EstadoTicket estado, String comentarios, Usuario usuario) {
		super();
		this.idTicket = idTicket;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.categoria = categoria;
		this.estado = estado;
		this.comentarios = comentarios;
		this.usuario = usuario;
	}

	public Ticket(int idTicket, String titulo, String descripcion, Categoria categoria, EstadoTicket estado,
			String comentarios, Usuario usuario) {
		super();
		this.idTicket = idTicket;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.estado = estado;
		this.comentarios = comentarios;
		this.usuario = usuario;
	}

	public Ticket(int idTicket, String titulo, String descripcion, LocalDateTime fechaCreacion, Categoria categoria,
			EstadoTicket estado, String comentarios, Usuario usuario, Tecnico tecnico) {
		super();
		this.idTicket = idTicket;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.categoria = categoria;
		this.estado = estado;
		this.comentarios = comentarios;
		this.usuario = usuario;
		this.tecnico = tecnico;
	}

	public int getIdTicket() {
		return idTicket;
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public EstadoTicket getEstado() {
		return estado;
	}

	public void setEstado(EstadoTicket estado) {
		this.estado = estado;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}
	
}
