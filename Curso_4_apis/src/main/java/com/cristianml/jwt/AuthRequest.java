package com.cristianml.jwt;

// Esta clase va a ser para la autenticación de la api

public class AuthRequest {

	private String correo;
	private String password;
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
