package com.cristianml.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/atributos")
	public String atributos(Model model) {
		Integer num1 = 12;
		Integer num2 = 13;
		Integer cifra = 12345;
		Date fecha = new Date();
		List<String> paises = new ArrayList<String>();
		paises.add("Bolivia");
		paises.add("Perú");
		paises.add("Chile");
		paises.add("Colombia");
		model.addAttribute("num1", num1);
		model.addAttribute("num2", num2);
		model.addAttribute("cifra", cifra);
		model.addAttribute("fecha", fecha);
		model.addAttribute("paises", paises);
		
		return "templates/atributos";
	}
	
	@GetMapping("/estaticos")
	public String estaticos(Model model) {
		return "templates/estaticos";
	}
	
	@GetMapping("/estaticos2")
	public String estaticos2 (Model model) {
		return "/templates/estaticos2";
	}
	
	@GetMapping("/ajax")
	public String ajax(Model model) {
		return "/templates/ajax";
	}
	
	@GetMapping("/peticion")
	public String peticion(@RequestParam("valor") String valor, Model model) {
		
		// Agregamos el valor que viene desde el método del script al model
		model.addAttribute("valor", valor);
		return "/templates/peticion"; 
	}
	
	@GetMapping("/fancybox")
	public String fancybox(Model model) {
		return "/templates/fancybox";
	}
	
	
	
}
