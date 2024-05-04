package com.cristianml.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	@Column(unique = true) // Con esto habilitamos para que el correo sea único y no se repita
	private String correo;
	private String telefono;
	private String password;
	private Integer estado;
	
	// Decimos que sea con carga perezosa con FetchType.LAZY
	// con el parámetro cascade hacemos que se relacionen con el autorizar
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
	// con JoinColumn hacemos que llame a la columna con la que se relacionará
	@JoinColumn(name = "usuarios_id")
	private List<AutorizarModel> autorizar;
	
	// Creamos constructor vacío
	public UsuarioModel() {
		super();
	}	
	
	// Creamos constructor sin el Id
	public UsuarioModel(String nombre, String correo, String telefono, String password, Integer estado,
			List<AutorizarModel> autorizar) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.password = password;
		this.estado = estado;
		this.autorizar = autorizar;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public List<AutorizarModel> getAutorizar() {
		return autorizar;
	}

	public void setAutorizar(List<AutorizarModel> autorizar) {
		this.autorizar = autorizar;
	}
	
	
	
}
