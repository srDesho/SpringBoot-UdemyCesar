package com.cristianml.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "categoria") // Importante que tenga el mismo nombre en la base de datos
public class CategoriaModel {

	// Importante que siempre nombremos la pk solo id a las tablas ya sea base de datos o aquí en el modelo java
	@Id
	// con .IDENTITY es para cuando ya tengamos creado en la base de datos si el ID es incremental lo pondrá automáticamente
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "está vacío")
	private String nombre;
	
	private String slug;
	

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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	
}
