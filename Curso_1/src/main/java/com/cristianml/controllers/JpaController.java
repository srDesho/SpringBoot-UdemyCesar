package com.cristianml.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianml.modelos.CategoriaModel;
import com.cristianml.service.CategoriaService;
import com.cristianml.utilidades.Utilidades;

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
	
	// Método para agregar nueva categoría
	@GetMapping("/categorias/add")
	public String categorias_add(Model model) {
		// Creamos un nuevo objeto de CategoriaModel
		CategoriaModel categoria = new CategoriaModel();
		model.addAttribute("categoria", categoria);
		return "/jpa_repository/categorias_add";
	}
	
	// Método post que recibe los datos
	@PostMapping("/categorias/add")
	public String categorias_add(@Valid CategoriaModel categoria, BindingResult result, Model model
			, RedirectAttributes flash) {
		
		// Validamos nuestro dato
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors()
			.forEach( err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			
			model.addAttribute("errores", errores);
			model.addAttribute("categoria", categoria);
			return "/jpa_repoitory/categorias_add";
			
			}
			// Verificamos si ya existe el slug, ver si no se duplicará en la base de datos
			// Antes creamos el slug obteniéndolo de nuestro objeto CategoriaModel y formateándolo con el método getSlug
			String slug = Utilidades.getSlug(categoria.getNombre());
				
			if (this.categoriaService.buscarPorSlug(slug) == false) {
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "La categoría ingresada ya existe en la base de datos.");
				return "redirect:/jpa-repository/categorias/add";
			} else {
				categoria.setSlug(slug);
				this.categoriaService.guardar(categoria);
				flash.addFlashAttribute("clase", "success");
				flash.addFlashAttribute("mensaje", "Se creó el registro exitosamente.");
				return "redirect:/jpa-repository/categorias/add";
			}
	}
	
	// Método editar una categoría
	@GetMapping("/categorias/editar/{id}")
	public String categorias_editar(@PathVariable("id") Integer id, Model model) {
		CategoriaModel categoria = this.categoriaService.buscarPorId(id);
		model.addAttribute("categoria", categoria);
		return "/jpa_repository/categorias_editar";
	}
	
	// Obtenemos el valor nuevo con el método post
	@PostMapping("/categorias/editar/{id}")
	public String categorias_editar_post(@Valid CategoriaModel categoria, BindingResult result, Model model
			, @PathVariable Integer id, RedirectAttributes flash) {

		// Validamos nuestro dato
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors()
			.forEach( err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			
			model.addAttribute("errores", errores);
			model.addAttribute("categoria", categoria);
			return "/jpa_repoitory/categorias_editar";
			
			}
		// Cambiamos el slug
		categoria.setSlug(Utilidades.getSlug(categoria.getNombre()));
		// Guardamos nuestra categoria con el nombre cambiado
		categoriaService.guardar(categoria);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se editó el registro exitosamente.");
		return "redirect:/jpa-repository/categorias/editar/"+id;
	}
	
	
	
}
