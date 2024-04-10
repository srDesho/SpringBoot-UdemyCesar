package com.cristianml.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cristianml.modelos.ProductosRestModel;
import com.cristianml.service.ClienteRestService;

@Controller
@RequestMapping("/cliente-rest")
public class ClienteRestController {
	
	// Obtenemos la ruta del apache que almacena las imágenes de subida
	@Value("${cristian.valores.base_url_upload}")
	private String base_url_upload;
	
	// Inyectamos nuestro servicio
	@Autowired
	private ClienteRestService clienteRestService;
	
	@GetMapping("")
	public String home(Model model) {
		// Listamos nuestros datos de la api, importante la api debe estar en línea(corriendo) para que no de error
		List<ProductosRestModel> datos = this.clienteRestService.listar();
		
		// Añadimos al model la dirección Url que se encarga de accesar a la carpeta designada
		// de la subida de archivos.
		model.addAttribute("base_url_upload", this.base_url_upload);
		
		// Agregamos los datos para poder ingresar a los datos desde la vista
		model.addAttribute("datos", datos);
		return "/cliente_rest/home";
	}
	
}
