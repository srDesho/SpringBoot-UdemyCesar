package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Agregamos la anotación @Controller para especificar que éste es un controlador
@Controller
@RequestMapping("/") // Se pone debaje del @Controller y sirve para indicar una ruta, similar al @GetMapping
public class HomeController {

	// Creamos un método que va a cargar una ruta y le asignamos el @GetMapping("/") para que sepa que 
	// cargará una ruta.
	
	@GetMapping("/") // Sólo slash devuelve algo vacío
	@ResponseBody // Sirve para mostrar texto en la ejecución del navegador (casi no se usa en lo laboral)
	public String home() {
		return "Hola mundito, más texto."; 
	}
	
	@GetMapping("/nosotros")
	@ResponseBody
	public String nostros() {
		return "Hola desde nosotros";
	}
	
	// Creando parámetros para pasarlos en la url con la forma llamada CLEAN URL
	// Los parámetros los creamos con:
	// @PathVariable(("nombre_ruta")) String nombre_variable => puede ser cualquier tipo de dato
	@GetMapping("/parametros/{id1}/{slug1}")
	@ResponseBody
	public String parametros(@PathVariable("id1") Long id, @PathVariable("slug1") String slug) {
		return "id = " + id + " || slug = " + slug;
	}
	
	// Otra manera de crear parámetros es con @RequestParam, se usa más para paginar o búsquedas
	// Ésta forma se llama QUERY STRING
	@GetMapping("/query_string") // No 
	@ResponseBody
	public String query_string(@RequestParam("id") Long id, @RequestParam("slug") String slug) {
		return "id = " + id + " || slug = " + slug;
	}
}
