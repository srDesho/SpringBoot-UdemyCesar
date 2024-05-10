package com.cristianml.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Esta anotación nos permite declarar las rutas optimizadas para desplegarlas como Api
@RestController
@RequestMapping("/api/v1")
public class ApiController {

	// Método de prueba como un "Hola mundo", ésta es nuestra primera vista con @RestController
	@GetMapping("/metodo")
	public String metodo_get() {
		return "Método GET";
	}
	
}
