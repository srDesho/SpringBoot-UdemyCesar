package com.cristianml.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/acceso")
public class AccesoController {

	@GetMapping("/login")
	// Recibimos los parámetros que spring security nos devolverá
	// El objeto principal nos sirve para obtener la información si algún usuario está logeado
	public String login(@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "logout", required = false) String logout, RedirectAttributes flash, Principal principal) {
		
		// Hacemos las validaciones
		
		// Si el Principal es es distinto de nulo entonces ya hay un usuario logeado
		if (principal != null) {
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Ya ha iniciado sesión anteriormente.");
			return "redirect:/";
		}
		
		// Si el error es distinto de nulo entonces hubo un error en autenticar los datos
		if (error != null) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "Los datos ingresados no son correcto, por favor inténtelo de nuevo.");
			return "redirect:/acceso/login";
		}
		
		// Si el logout es distinto de nulo entonces el usuario cerró sesión
		if (logout != null) {
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Ha cerrado sesión exitosamente.");
			return "redirect:/acceso/login";
		}
		
		
		return "acceso/login";
	}
	
}
