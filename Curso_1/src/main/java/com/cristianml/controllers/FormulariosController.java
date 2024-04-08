package com.cristianml.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RedirectAttributesMethodArgumentResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianml.modelos.InteresesModel;
import com.cristianml.modelos.PaisModel;
import com.cristianml.modelos.Usuario2Model;
import com.cristianml.modelos.Usuario3Model;
import com.cristianml.modelos.UsuarioCheckboxModel;
import com.cristianml.modelos.UsuarioModel;
import com.cristianml.modelos.UsuarioUploadModel;
import com.cristianml.utilidades.Utilidades;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {

	// Creamos una ruta para el home de formularios
	
	@GetMapping("")
	public String home() {
		return("formularios/home");
	}
	
	// ================================ Formulario Simple ==================================
	@GetMapping("/simple")
	public String simple() {
		return "/formularios/simple";
	}
	
	// Creamos el método con @PostMapping para recibir el formulario 
	@PostMapping("/simple")
	@ResponseBody
	public String simple_post(@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("password") String password) {
		return "username= " + username + "<br>email= " + email + "<br>password= " + password;
	}
	
	// ================================ Formulario De Objetos ==================================
	@GetMapping("/objeto")
	public String objeto() {
		return "/formularios/objeto";
	}
	
	// Creamos el método con @PostMapping para recibir valores del formulario a travez de la entidad UsuarioModel
	@PostMapping("/objeto")
	@ResponseBody
	public String objeto_post(UsuarioModel usuario) {
		return "<h1>Objeto</h1>Nombre de usuario= " + usuario.getUsername() + "<br>Email= " + usuario.getEmail()
		+ "<br>Password= " + usuario.getPassword();
	}
	
	// ============ Formulario De Objetos 2, otra manera de recibir datos del formulario============
		@GetMapping("/objeto2")
		public String objeto2(Model model) {
			model.addAttribute("usuario", new UsuarioModel());
			return "/formularios/objeto2";
		}
	
	// Obtenemos los datos del formulario a travez de @PostMapping()
		@PostMapping("/objeto2")
		@ResponseBody
		public String objeto2_post(UsuarioModel usuario) {
			return "<h1>Objeto 2</h1>Nombre de usuario= " + usuario.getUsername() + "<br>Email= " + usuario.getEmail()
			+ "<br>Password= " + usuario.getPassword();
		}
	
	// ============================== Formulario validaciones ===================================
		@GetMapping("/validaciones")
		public String validaciones(Model model) {
			model.addAttribute("usuario", new Usuario2Model());
			return "/formularios/validaciones";
		}
		
		// Método para procesar los datos con @PostMapping()
		@PostMapping("/validaciones")
		// @Valid nos sirve para que el método pueda hacer uso de las validaciones de nuestra entidad mapeada
		// seguido de este parámetro agregamos el BingdingResult result porque van de la mano y es exigido por java
		public String validaciones_post( @Valid Usuario2Model usuario, BindingResult result, Model model) {
			if(result.hasErrors()) {
				Map<String, String> errores = new HashMap<>();
				result.getFieldErrors()
				.forEach( err -> {
					errores.put(err.getField(),
							"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
				});
				
				model.addAttribute("errores", errores);
				model.addAttribute("usuario", usuario);
				return "/formularios/validaciones";
			}
			// model.addAttribute("usuario", usuario) le pasa el model a la vista mediante ${@usuario}
			model.addAttribute("usuario", usuario);
			return "/formularios/validaciones_result";
		}
	
		// ================================ Formulario select dinámico ==========================================
		@GetMapping("/select-dinamico")
		public String select_dinamico(Model model) {
			
			
			
			// Agregamos al model para que tenga almacenado el objeto usuario y pueda ser llamados en la vista
			model.addAttribute("usuario", new Usuario3Model());
			return "/formularios/select_dinamico";
		}
		
		// Recibimos los datos desde la vista
		@PostMapping("/select-dinamico")
		public String select_dinamico_post(@Valid Usuario3Model usuario, BindingResult result, Model model ) {
			if(result.hasErrors()) {
				Map<String, String> errores = new HashMap<>();
				result.getFieldErrors()
				.forEach( err -> {
					errores.put(err.getField(),
							"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
				});
				
				model.addAttribute("errores", errores);
				model.addAttribute("usuario", usuario);
				return "/formularios/select_dinamico";
			}
			// model.addAttribute("usuario", usuario) le pasa el model a la vista mediante ${@usuario}
			model.addAttribute("usuario", usuario);
			return "/formularios/select_dinamico_result";
		}
		
		// ================================= Formulario checkbox =====================================
		@GetMapping("/checkbox")
		public String checkbox(Model model) {
			model.addAttribute("usuario", new UsuarioCheckboxModel());
			return "/formularios/checkbox";
		}
		
		@PostMapping("/checkbox")
		public String checkbox_post(@Valid UsuarioCheckboxModel usuario, BindingResult result, Model model) {
			if(result.hasErrors()) {
				Map<String, String> errores = new HashMap<>();
				result.getFieldErrors()
				.forEach( err -> {
					errores.put(err.getField(),
							"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
				});
				
				model.addAttribute("errores", errores);
				model.addAttribute("usuario", usuario);
				return "/formularios/checkbox";
			}
			// model.addAttribute("usuario", usuario) le pasa el model a la vista mediante ${@usuario}
			model.addAttribute("usuario", usuario);
			return "/formularios/checkbox_result";
		}
		
		// Creación de mensajes flash
		@GetMapping("/flash")
		public String flash(Model model) {
			model.addAttribute("usuario", new UsuarioModel());
			return "/formularios/flash";
		}
		
		// Obtenemos los datos
		@PostMapping("/flash")
		public String flash_post(UsuarioModel usuario, RedirectAttributes flash) {
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Ejemplo de mensaje flash con BootsTrap");
			return "redirect:/formularios/flash-respuesta"; // Redirecciona al flash_respuesta()
		}
		
		// Redireccionamos a otra vista donde se mostrará el mensaje flash
		@GetMapping("/flash-respuesta")
		public String flash_respuesta(Model model) {
			return "/formularios/flash_respuesta";
		}
		
		// =============================== Formulario Upload  ==============================
		// Creamos la variable que almacena la ruta desde la variable de application.properties
		@Value("${cristian.valores.ruta_upload}")
		private String ruta_upload; 
		
		// Creamos la ruta para nuestro formulario upload
		@GetMapping("/upload")
		public String upload(Model model) {
			model.addAttribute("usuario", new UsuarioUploadModel());
			return "/formularios/upload";
		}
		
		// Creamos el método Post para que reciba los datos de la vista upload
		@PostMapping("/upload")
		public String upload_post(@Valid UsuarioUploadModel usuario, BindingResult result, Model model, 
				@RequestParam("archivoImagen") MultipartFile multiPart, RedirectAttributes flash) {
			
			if(result.hasErrors()) {
				Map<String, String> errores = new HashMap<>();
				result.getFieldErrors()
				.forEach( err -> {
					errores.put(err.getField(),
							"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
				});
				
				model.addAttribute("errores", errores);
				model.addAttribute("usuario", usuario);
				return "/formularios/upload";
			}
			// Verificamos si no cargo ninguna imagen en el campo de la vista
			if (multiPart.isEmpty()) {
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la imágen es obligatorio, debe ser JPG|JPEG|PNG");
				return "redirect:/formularios/upload"; // Redirecciona al flash_respuesta()
			}
			// Si cargó una imágen verificamos el mimetype
			if (!multiPart.isEmpty()) {
				// guardamos en una variable el resultado de nuestra utilidad que guarda la imágen
				// this.ruta_upload+"images" => ruta_upload es la variable de nuestra clase que almacena la ruta
				// que viene desde application.properties
				// this.ruta_upload+"images" => images será cualquier carpeta que creamos dentro de la carpeta de la ruta
				// Obligatorio poner las últimas barras en images\\ o depende si es images/
				String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload+"images/");
				
				// Verificamos el valor de nombreImagen
				if (nombreImagen == "no") {
					flash.addFlashAttribute("clase", "danger");
					flash.addFlashAttribute("mensaje", "El archivo para la imágen no es válido, debe ser JPG|JPEG|PNG");
					return "redirect:/formularios/upload"; // Redirecciona al flash_respuesta()
				} 
				if (nombreImagen != null) {
					usuario.setFoto(nombreImagen);
				}
				
			}
			model.addAttribute("usuario", usuario);
			return "/formularios/upload_respuesta";
		}
		
		// =============================== Campos genéricos mediante @ModelAttribute  ==============================
		// ésto nos servirá para tener la lista de países almacenada y que pueda ser llamada en varios métodos
		// también nos sirve para almacenar rutas de pack de imagenes externo que se usaran en distinto métodos
		// categorias de productos, cadenas de texto, etc.
		@ModelAttribute
		public void setGenericos(Model model) {
			List<PaisModel> paises = new ArrayList<PaisModel>();
			paises.add(new PaisModel(1, "Bolivia"));
			paises.add(new PaisModel(2, "Chile"));
			paises.add(new PaisModel(3, "Argentina"));
			paises.add(new PaisModel(3, "Colombia"));
			paises.add(new PaisModel(3, "España"));
			model.addAttribute("paises", paises);
			
			// Creamos una lista para los interese
			List<InteresesModel> intereses = new ArrayList<InteresesModel>();
			intereses.add(new InteresesModel(1, "Música"));
			intereses.add(new InteresesModel(2, "Deportes"));
			intereses.add(new InteresesModel(3, "Religión"));
			intereses.add(new InteresesModel(4, "Economía"));
			intereses.add(new InteresesModel(5, "Política"));
			// agregamos al model para que sea almacenado y ser accesado por la vista
			model.addAttribute("intereses", intereses);
		}
	
}
