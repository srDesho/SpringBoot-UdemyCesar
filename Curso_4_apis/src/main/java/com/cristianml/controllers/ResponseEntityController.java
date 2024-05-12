package com.cristianml.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristianml.models.EjemploModel;

// Ésta es la manera real en la que se crean los endpoints, ya que lo normal que vamos a retornar datos en formato json

@RestController
@RequestMapping("/api/v1")
public class ResponseEntityController {

	// En general lo que más vamos a utilizar al crear Apis es el objeto ResponseEntity
	@GetMapping("/response-entity")
	public ResponseEntity<String> metodo_get() {
		// Retornamos un valor de tipo ResponseEntity, y el método .ok() sirve para retornar el estado Http 200
		return ResponseEntity.ok("Método GET desde ResponseEntity");
	}
	
	// Pasando Parámetros
	@GetMapping("/response-entity/{id}")
	public ResponseEntity metodo_get_parametro(@PathVariable("id") Integer id) {
		return ResponseEntity.ok("Método con parámetros = " + id);
	}
	
	@PostMapping("/response-entity")
	public ResponseEntity<String> metodo_post() {
		return ResponseEntity.ok("Método POST desde ResponseEntity");
	}
	
	// Método para recibir datos en formato Json
	// Debemos manejarlo con un modelo creado para manejar los datos

	// Copiamos el formato json para no olvidarnos
	
	/*
		 {
		 "nombre" : "Cristian Montaño",
		 "correo" : "cristian@gmail.com",
		 "precio" : 123,
		 "descripcion" : "descripcion con ñandú"
		 } 
	*/
	
	// Para esto usarlo en postman debemos enviarlo con el cabecero Content-Type	application/json
	// Es equivalente a que esté marcado el Json en la pestaña Body de postman
	// El formato debe estar copiado en la pestaña Body con la opción raw
	@PostMapping("/response-entity-json")
	public ResponseEntity<String> metodo_post_json(@RequestBody EjemploModel request) {
		return ResponseEntity.ok("nombre="+request.getNombre() + " | correo=" + request.getCorreo() + " | precio="+request.getPrecio()
		+ " | descripcion="+request.getDescripcion());
	}
	
	@PutMapping("/response-entity")
	public ResponseEntity<String> metodo_put() {
		return ResponseEntity.ok("Método PUT desde ResponseEntity");
	}
	
	@DeleteMapping("/response-entity")
	public ResponseEntity<String> metodo_delete() {
		return ResponseEntity.ok("Método DELETE desde ResponseEntity");
	}
}
