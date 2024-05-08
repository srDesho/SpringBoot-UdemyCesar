package com.cristianml.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianml.models.AutorizarModel;
import com.cristianml.models.UsuarioModel;
import com.cristianml.services.AutorizarService;
import com.cristianml.services.UsuarioService;

@Controller
@RequestMapping("/acceso")
public class AccesoController {
	
	// Inyectamos los servicios de las entidades y el bean que encripta las contraseñas que creamos en nuestra clase Seguridad
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizarService autorizarService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
	
	// Creamos el método para Restrar usuarios
	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("usuario", new UsuarioModel());
		return "/acceso/registro";
	}
	
	// Creamos el método registro_post
	@PostMapping("/registro")
	public String registro_post(@Valid UsuarioModel usuario, BindingResult result, RedirectAttributes flash, Model model) {

		// Validamos los datos
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors()
			.forEach( err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			
			model.addAttribute("errores", errores);
			model.addAttribute("usuario", usuario);
			return "/acceso/registro";
			
			}
		// Creamos el usuario
		UsuarioModel guardar = this.usuarioService.guardar(new UsuarioModel(usuario.getNombre(), usuario.getCorreo()
				, usuario.getTelefono(), this.bCryptPasswordEncoder.encode(usuario.getPassword()), 1, null));
		
		// Creamos algún rol
		this.autorizarService.guardar(new AutorizarModel("ROLE_USER", guardar));
		// redireccionamos
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Te has registrado exitosamente.");
		return "redirect:/acceso/registro";
	}
	
}
