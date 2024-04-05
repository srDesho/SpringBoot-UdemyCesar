package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cristianml.modelos.UsuarioModel;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {

	// Creamos una ruta para el home de formularios
	
	@GetMapping("")
	public String home() {
		return("formularios/home");
	}
	
	// ================================ Formulario Simple ==================================
	@GetMapping("/simple")
	public String simple() {
		return "/formularios/simple";
	}
	
	// Creamos el método con @PostMapping para recibir el formulario 
	@PostMapping("/simple")
	@ResponseBody
	public String simple_post(@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("password") String password) {
		return "username= " + username + "<br>email= " + email + "<br>password= " + password;
	}
	
	// ================================ Formulario De Objetos ==================================
	@GetMapping("/objeto")
	public String objeto() {
		return "/formularios/objeto";
	}
	
	// Creamos el método con @PostMapping para recibir valores del formulario a travez de la entidad UsuarioModel
	@PostMapping("/objeto")
	@ResponseBody
	public String objeto_post(UsuarioModel usuario) {
		return "<h1>Objeto</h1>Nombre de usuario= " + usuario.getUsername() + "<br>Email= " + usuario.getEmail()
		+ "<br>Password= " + usuario.getPassword();
	}
}
