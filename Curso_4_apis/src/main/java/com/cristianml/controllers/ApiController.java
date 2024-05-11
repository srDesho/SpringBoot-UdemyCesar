package com.cristianml.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristianml.models.EjemploModel;

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
	
	// Creación de método post para recibir parámetros en formato jason
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
	
	@PostMapping("/metodo-json")
	public String metodo_json(@RequestBody EjemploModel request) {
		return "nombre="+request.getNombre() + " | correo=" + request.getCorreo() + " | precio="+request.getPrecio()
		+ " | descripcion="+request.getDescripcion();
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
