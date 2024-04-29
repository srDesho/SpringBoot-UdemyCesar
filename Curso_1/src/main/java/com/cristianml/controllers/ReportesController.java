package com.cristianml.controllers;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;


@Controller
@RequestMapping("/reportes")
public class ReportesController {
	
	// Agregamos la ruta de nuestra aplicación 
		@Value("${cristian.valores.base.url_application}")
		private String base_url_application;

	@GetMapping("")
	public String home() {
		return "/reportes/home";
	}
	
	// ========================================= PDF ============================================
	
	// Creamos un atributo final, de tipo TemplateEngine que importamos de Thymeleaf que será es recurso que 
	// nos permitirá construir la renderización del archivo de la vista con la cuál vamos a trabajar.
	
	private final TemplateEngine templateEngine;
	
	// Creamos un constructor para configurar el templateEngine
	public ReportesController(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	// Inyectamos un ServletContext de java
	@Autowired
	private ServletContext servletContext;
	
	// Creamos el método para pdf, con el objeto ResponseEntity
	@GetMapping("/pdf")
	// El parámetro de tipo HttpServletRequest nos va a permitir recoger el request
	public ResponseEntity<?> productos_pdf(HttpServletRequest request, HttpServletResponse response) {
		// Creamos un objeto de tipo WebContext de Thymelef
		WebContext context = new WebContext(request, response, servletContext);
		// al objeto debemos setearle las siguientes variables
		// El setVariable nos permite pasar parámetros a la vista, trabaja como el addAtributes del Model
		context.setVariable("titulo", "PDF dinámico desde SpringBoot") ;
		context.setVariable("ruta", this.base_url_application);
		
		// Creamos la estructura de la ruta, spring ya sabe que la vista se encuentra en resources/template del proyecto
		// así que sólo debemos buscar nuestra carpeta y la vista -> "reportes/pdf"
		String html = this.templateEngine.process("reportes/pdf", context);
		
		// Creamos un objeto de tipo ByteArrayOutputStream de IO 
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		
		// Creamos un objeto de tipo ConverterProperties de itextpdf que instalamos en nuestro pom
		ConverterProperties converterProperties = new ConverterProperties();
		// seteamos la ruta de nuestro proyecto para que se configuren los recursos internamente
		converterProperties.setBaseUri(this.base_url_application);
		
		// Llamamos a la clase HtmlConverter.convertToPdf()
		// Le pasamos de parámetros nuestros objetos creados anteriormente
		HtmlConverter.convertToPdf(html, target, converterProperties);
		
		// Convertimos a un byte
		byte[] bytes = target.toByteArray();
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
	}
	
	
	
}
