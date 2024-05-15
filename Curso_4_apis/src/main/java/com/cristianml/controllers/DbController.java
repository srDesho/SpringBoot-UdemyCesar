package com.cristianml.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristianml.models.CategoriaModel;
import com.cristianml.services.CategoriaService;

@RestController
@RequestMapping("/api/v1")
public class DbController {
	
	// Existe 2 tipos de retorno en nuestras apis:
	// 1. ResponseEntity = retorna todo lo que es transaccional, o sea que va a ejecutar alguna acción
	// como guardar, editar, eliminar.
	// 2. List<TipoDeDatoUModelo> = esto es para cuando queremos listar.
	
	@Autowired
	private CategoriaService categoriaService;

	// ================================================ CATEGORIAS ================================================
	
	// Método listar
	@GetMapping("/categorias")
	public List<CategoriaModel> categorias() {
		return this.categoriaService.listar();
	}
	
	// Método para listar categorias
	
	
	
	
	
	
	// ================================================ PRODUCTOS ================================================
	
	
}
