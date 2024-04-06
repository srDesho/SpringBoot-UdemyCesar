package com.cristianml.modelos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

// Esta clase trabaja con la vista select_dinamico
public class Usuario3Model {

	// Mappeamos con validation que agregamos en nuestra pom dependencia
	@NotEmpty(message = "está vacía.")
	private String username;
	@NotEmpty(message = "está vacía.")
	@Email(message = "ingresado no es válido.")
	private String email;
	@NotEmpty(message = "está vacía.")
	private String password;
	
	private PaisModel paisId;
	
	
	
	public PaisModel getPaisId() {
		return paisId;
	}
	public void setPaisId(PaisModel paisId) {
		this.paisId = paisId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
