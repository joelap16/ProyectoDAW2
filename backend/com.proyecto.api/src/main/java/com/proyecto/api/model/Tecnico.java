package com.proyecto.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_tecnico")
public class Tecnico {

    @Id
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Usuario usuario;
    
    @Column(name = "nombre_tecnico")
	private String nombreTecnico;
	
	@Column(name = "apellido_tecnico")
	private String apellidoTecnico;
	
	@Column(name = "email_tecnico")
	private String emailTecnico;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "tecnico")
    @JsonManagedReference
    private List<Ticket> ticketsAsignados;

	public Tecnico() {
		super();
	}

	public Tecnico(int id, Usuario usuario, String nombreTecnico, String apellidoTecnico, String emailTecnico,
			Categoria categoria, List<Ticket> ticketsAsignados) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.nombreTecnico = nombreTecnico;
		this.apellidoTecnico = apellidoTecnico;
		this.emailTecnico = emailTecnico;
		this.categoria = categoria;
		this.ticketsAsignados = ticketsAsignados;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNombreTecnico() {
		return nombreTecnico;
	}

	public void setNombreTecnico(String nombreTecnico) {
		this.nombreTecnico = nombreTecnico;
	}

	public String getApellidoTecnico() {
		return apellidoTecnico;
	}

	public void setApellidoTecnico(String apellidoTecnico) {
		this.apellidoTecnico = apellidoTecnico;
	}

	public String getEmailTecnico() {
		return emailTecnico;
	}

	public void setEmailTecnico(String emailTecnico) {
		this.emailTecnico = emailTecnico;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Ticket> getTicketsAsignados() {
		return ticketsAsignados;
	}

	public void setTicketsAsignados(List<Ticket> ticketsAsignados) {
		this.ticketsAsignados = ticketsAsignados;
	}
	
}
