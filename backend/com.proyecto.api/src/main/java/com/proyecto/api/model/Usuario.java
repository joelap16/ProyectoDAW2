package com.proyecto.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "tbl_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private int idUsuario;
	
	@Column(name = "name_usua")
	private String nomUsuario;
	
	@Column(name = "surname_usua")
	private String apeUsuario;
	
	@Column(name = "email_usua")
	private String emaUsuario;
	
	@ManyToOne
	@JoinColumn(name = "rol_id")
	private RolUsuario rol;
	
	public Usuario() {
		super();
	}

	public Usuario(int idUsuario, String nomUsuario, String apeUsuario, String emaUsuario, RolUsuario rol) {
		super();
		this.idUsuario = idUsuario;
		this.nomUsuario = nomUsuario;
		this.apeUsuario = apeUsuario;
		this.emaUsuario = emaUsuario;
		this.rol = rol;
	}	
	
	public Usuario(String nomUsuario, String apeUsuario, String emaUsuario, RolUsuario rol) {
		super();
		this.nomUsuario = nomUsuario;
		this.apeUsuario = apeUsuario;
		this.emaUsuario = emaUsuario;
		this.rol = rol;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public String getApeUsuario() {
		return apeUsuario;
	}

	public void setApeUsuario(String apeUsuario) {
		this.apeUsuario = apeUsuario;
	}

	public String getEmaUsuario() {
		return emaUsuario;
	}

	public void setEmaUsuario(String emaUsuario) {
		this.emaUsuario = emaUsuario;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}
	
	
}
