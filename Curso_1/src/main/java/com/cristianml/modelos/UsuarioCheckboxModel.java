package com.cristianml.modelos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuarioCheckboxModel {
	// Mappeamos con validation que agregamos en nuestra pom dependencia
		@NotEmpty(message = "está vacía.")
		private String username;
		@NotEmpty(message = "está vacía.")
		@Email(message = "ingresado no es válido.")
		private String email;
		@NotEmpty(message = "está vacía.")
		private String password;
		
		private List<InteresesModel> intereses;
		
		

		public List<InteresesModel> getIntereses() {
			return intereses;
		}

		public void setIntereses(List<InteresesModel> intereses) {
			this.intereses = intereses;
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
