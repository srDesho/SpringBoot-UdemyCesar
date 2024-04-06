package com.cristianml.modelos;

public class PaisModel {
	private Integer idPais;
	private String nombre;
	
	public PaisModel() {
	}

	public PaisModel(Integer idPais, String nombre) {
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
}
