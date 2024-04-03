package com.cristianml.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/templates")
public class TemplatesController {
	
	@GetMapping("")
	// Pasamos parámetros con una variable String para mostrar en el html con thymeleaf, pasamos con un model
	public String home(Model model) {
		// Creamos variables
		String nombre = "Cristian Montaño Laime";
		String pais = "Bolivia";
		
		// Agregamos al model con un nombre entre comillas el cual será usado para ser llamado desde html thymeleaf
		model.addAttribute("nombre", nombre);
		model.addAttribute("pais", pais);
		return "templates/home.html";
	}
}
