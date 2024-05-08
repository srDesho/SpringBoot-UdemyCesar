package com.cristianml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianml.seguridad.PermisosService;

@Controller
@RequestMapping("/restringido")
public class RestringidoController {
	
	// Inyectamos este servicio para poder verificar los roles en cualquier método que querramos
	@Autowired
	private PermisosService permisosService;
	
	
	// Vamos a hacer que éste home sólo pueda ser visto por el admin
	@GetMapping("")
	public String home(RedirectAttributes flash) {
		
		// Preguntamos si el usuario tiene el rol de ROLE_ADMIN
		if (this.permisosService.comprobarRol("ROLE_ADMIN")) {
			return "/restringido/home";
		} else {
			// Si no lo tiene, le mostramos un mensaje flash y redireccionamos al home de la aplicación
			flash.addFlashAttribute("clase", "warning");
			flash.addFlashAttribute("mensaje", "No tienes acceso a este contenido.");
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/restringido-2")
	public String restringido_2() {
		return "/restringido/restringido_2";
	}
	
}
