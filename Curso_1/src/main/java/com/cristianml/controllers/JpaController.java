package com.cristianml.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cristianml.service.CategoriaService;

@Controller
@RequestMapping("/jpa-repository")
public class JpaController {
	
	// Inyectamos los servicios que nos permitirán acceder los datos de la DB
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("")
	public String home(Model model) {
		
		return "/jpa_repository/home";
	}
	
	// ================================ CATEGORÍAS ===================================
	@GetMapping("/categorias")
	public String categorias(Model model) {
		
		// objetemos la liste de categorias y la enviamos con model a la vista
		model.addAttribute("datos", this.categoriaService.listar());
		return "/jpa_repository/categorias";
	}
	
}
