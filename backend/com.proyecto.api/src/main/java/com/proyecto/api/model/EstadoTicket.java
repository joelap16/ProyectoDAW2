package com.proyecto.api.model;

import com.proyecto.api.enums.EstadosTicket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_estadoTicket")
public class EstadoTicket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estado")
	private int idEstado;
	
	@Column(name = "nombre_estado")
	@Enumerated(EnumType.STRING)
	private EstadosTicket nombreEstado;

	public EstadoTicket() {
		super();
	}

	public EstadoTicket(int idEstado, EstadosTicket nombreEstado) {
		super();
		this.idEstado = idEstado;
		this.nombreEstado = nombreEstado;
	}

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public EstadosTicket getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(EstadosTicket nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
}
