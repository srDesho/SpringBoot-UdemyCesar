package com.cristianml.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "autorizar")
public class AutorizarModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Basic
	private String nombre;
	
	// Creamos la relación con AutorizarModel para que no genere error
	@OneToOne
	@JoinColumn(name = "usuarios_id")
	private UsuarioModel usuarioId;
	

	public AutorizarModel() {
		super();
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
	
	

	public AutorizarModel(String nombre, UsuarioModel usuarioId) {
		super();
		this.nombre = nombre;
		this.usuarioId = usuarioId;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UsuarioModel getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UsuarioModel usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
	
}
