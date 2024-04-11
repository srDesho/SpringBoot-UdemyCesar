package com.cristianml.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productos")
public class ProductoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "Esta vacío")
	private String nombre;
	private String slug;
	@NotEmpty(message = "Esta vacío")
	private String descripcion;
	@NotNull(message = "No puede ser nulo")
	@Min(5) // Valor mínimo
	// @Max(5000) // Valor máximo
	private Integer precio;
	private String foto;
	
	// No se recomienda mucho crear relaciones de muchos a muchos (n a n)
	@OneToOne
	@JoinColumn(name = "categoria_id")
	private CategoriaModel categoriaId;

	 
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public CategoriaModel getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(CategoriaModel categoriaId) {
		this.categoriaId = categoriaId;
	}
	
}
