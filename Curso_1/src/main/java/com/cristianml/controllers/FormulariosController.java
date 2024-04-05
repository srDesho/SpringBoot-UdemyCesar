package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {

	// Creamos una ruta para el home de formularios
	
	@GetMapping("")
	public String home() {
		return("formularios/home");
	}
}
