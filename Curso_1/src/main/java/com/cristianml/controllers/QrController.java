package com.cristianml.controllers;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cristianml.service.QrCodeService;
import com.google.zxing.WriterException;

@Controller
@RequestMapping("/qr")
public class QrController {

	// Inyectamos nuestro servicio que se encargará de crear el qr
	@Autowired
	QrCodeService qrCodeService;
	
	@GetMapping("")
	public String home() {
		return "qr/home";
	}
	
	// Creamos nuestro método que se encargará de llamar al servicio para crear el qr y luego direccionarlo a una vista
	@GetMapping("/crear")
	public String crear(Model model) {
		// Creamos una variable que contenga un texto, puede ser una URL
		String url = "google.com";
		// Creamos nuestra variable para llamar al método que crea el qr
		byte[] image = new byte[0];
		
		// Creamos una excepción para controlar errores
		try {
			// llamamos al método del servicio qr
			image = qrCodeService.crearQR(url, 250, 250);
		} catch (WriterException | IOException e) {
			// Podemos redireccionarlo a la misma vista pero con un mensaje flash
		}
		
		// Convertimos el byte array en base64 String
		String qrcode = Base64.getEncoder().encodeToString(image);
		
		// Pasamos el model
		model.addAttribute("qrcode", qrcode);
		model.addAttribute("url", url);
		return "/qr/crear";
	}
	
}
