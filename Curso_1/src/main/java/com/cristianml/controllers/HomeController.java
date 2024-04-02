package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Agregamos la anotación @Controller para especificar que éste es un controlador
@Controller
public class HomeController {

	// Creamos un método que va a cargar una ruta y le asignamos el @GetMapping("/") para que sepa que 
	// cargará una ruta.
	
	@GetMapping("/")
	public String home() {
		return "";
	}
}
