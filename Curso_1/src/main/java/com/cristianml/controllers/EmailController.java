package com.cristianml.controllers;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianml.service.EmailService;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	// Inyectamos la dependencia(el servicio)
	@Autowired
	EmailService emailService;

	@GetMapping("")
	public String home(Model model) {
		return "email/home";
	}
	
	// Método para enviar el correo a traves del servicio
	@GetMapping("/send")
	public String send(Model model, RedirectAttributes flash) throws AddressException, MessagingException {
		// Creamos el mensaje
		String mensaje = "Hola desde un mensaje desde Spring Boot<hr/><strong>mensaje en negrita</strong>";
		this.emailService.sendEmail("cualquierasinmiedo@gmail.com", "Mensaje desde Spring", mensaje);
		
		// Agregamos la alerta del mensaje rápido
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se envió correctamente.");
		return "redirect:/email";
		
	}
}
