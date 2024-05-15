package com.cristianml.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Este modelo va a ser para la autenticación Web Token(JWT) de los usuarios

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	
	// Le decimos que el campo sea único, nos servirá más cuando se genera un insert en la tabla
	@Column(nullable = false, unique = true)
	private String correo;
	private String telefono;
	
	// nullable = false: Indica que la columna correspondiente en la base de datos no puede contener valores nulos.
	// length = 64: Especifica la longitud máxima de la columna en la base de datos.
	@Column(nullable = false, length  = 64)
	private String password;
	
	public UsuarioModel() {
		super();
	}

	// Creamos el constructor sin el Id porque luego vamos a crear registros
	public UsuarioModel(String nombre, String correo, String telefono, String password) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.password = password;
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
	
	
	
}
