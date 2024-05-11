package com.cristianml.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Ésta es la manera real en la que se crean los endpoints, ya que lo normal que vamos a retornar datos en formato json

@RestController
@RequestMapping("/api/v1")
public class ResponseEntityController {

	// En general lo que más vamos a utilizar al crear Apis es el objeto ResponseEntity
	@GetMapping("/response-entity")
	public ResponseEntity<String> metodo_get() {
		// Retornamos un valor de tipo ResponseEntity, y el método .ok() sirve para retornar el estado Http 200
		return ResponseEntity.ok("Método GET");
	}
	
}
