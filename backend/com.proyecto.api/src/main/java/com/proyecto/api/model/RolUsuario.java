package com.proyecto.api.model;

import com.proyecto.api.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_rolUsuario")
public class RolUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rol_id")
	private int idRol;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nombre_rol")
	private Role nombreRol; // ADMIN, TECNICO, USUARIO

	public RolUsuario() {
		super();
	}

	public RolUsuario(int idRol, Role nombreRol) {
		super();
		this.idRol = idRol;
		this.nombreRol = nombreRol;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public Role getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(Role nombreRol) {
		this.nombreRol = nombreRol;
	}
	
}
