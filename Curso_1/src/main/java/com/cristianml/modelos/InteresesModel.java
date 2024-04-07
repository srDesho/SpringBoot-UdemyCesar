package com.cristianml.modelos;

public class InteresesModel {

	private Integer id;
	private String nombre;
	
	public InteresesModel() {
		super();
	}

	public InteresesModel(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
