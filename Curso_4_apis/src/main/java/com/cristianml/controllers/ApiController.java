package com.cristianml.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	// Trabajando con parámetros
	@GetMapping("/metodo/{id}")
	public String metodo_get_parametro(@PathVariable("id") String id) {
		return "Método get con parámetros = " + id;
	}
	
	@PostMapping("/metodo")
	public String metodo_post() {
		return "Método POST";
	}
	
	@PutMapping("/metodo")
	public String metodo_put() {
		return "Método PUT";
	}
	
	@DeleteMapping("/metodo")
	public String metodo_delete() {
		return "Método DELETE";
	}
}
