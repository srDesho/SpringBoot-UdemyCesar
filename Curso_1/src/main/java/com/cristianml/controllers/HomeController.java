package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Agregamos la anotación @Controller para especificar que éste es un controlador
@Controller
public class HomeController {

	// Creamos un método que va a cargar una ruta y le asignamos el @GetMapping("/") para que sepa que 
	// cargará una ruta.
	
	@GetMapping("/") // Sólo slash devuelve algo vacío
	@ResponseBody // Sirve para mostrar texto en la ejecución del navegador (casi no se usa en lo laboral)
	public String home() {
		return "Hola mundito";
	}
}
