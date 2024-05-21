package com.cristianml.jwt;

// Este modelo se va a utilizar para parsear la estructura de nuestro token, la respuesta que se va
// a entregar cuando el usuario se logee.
// Le vamos a pasar el token y el correo o también el nombre, lo que querramos.
//En este caso vamos a mostrar también el correo
public class AuthResponse {

	private String correo;
	private String accesToken;
	
	public AuthResponse() {
		super();
	}

	public AuthResponse(String correo, String accesToken) {
		super();
		this.correo = correo;
		this.accesToken = accesToken;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getAccesToken() {
		return accesToken;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}
	
}
