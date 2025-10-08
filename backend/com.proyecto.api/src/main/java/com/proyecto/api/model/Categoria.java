package com.proyecto.api.model;

import com.proyecto.api.enums.CategoriasEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private int idCategoria;
	
	@Column(name = "nombre_categoria")
	@Enumerated(EnumType.STRING)
	private CategoriasEnum nombreCategoria;
	/*	
	SOPORTE_TECNICO,
	SOFTWARE,
	HARDWARE,
	BASE_DE_DATOS, 
	MANTENIMIENTO, // MANTENIMIENTO DE EQUIPOS
	ACCESO_CUENTAS,	// USUARIOS BLOQUEADOS, SOLICITUDES DE ACCESO
	INCIDENCIA // CAIDAS DEL SISTEMA, ERRORES CRITICOS
	*/

	public Categoria() {
		super();
	}
	public Categoria(int idCategoria, CategoriasEnum nombreCategoria) {
		super();
		this.idCategoria = idCategoria;
		this.nombreCategoria = nombreCategoria;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public CategoriasEnum getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(CategoriasEnum nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}	
	
}
