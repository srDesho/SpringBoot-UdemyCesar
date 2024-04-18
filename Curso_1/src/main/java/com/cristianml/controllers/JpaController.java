package com.cristianml.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianml.modelos.CategoriaModel;
import com.cristianml.modelos.ProductoModel;
import com.cristianml.service.CategoriaService;
import com.cristianml.service.ProductoService;
import com.cristianml.utilidades.Utilidades;

@Controller
@RequestMapping("/jpa-repository")
public class JpaController {
	
	// Inyectamos los servicios que nos permitirán acceder los datos de la DB
	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProductoService productoService;
	
	// Agregamos nuestra ruta de imágenes del servidor
	@Value("${cristian.valores.base_url_upload}")
	private String base_url_upload;
	
	@Value("${cristian.valores.ruta_upload}")
	private String ruta_upload;
	
	@GetMapping("")
	public String home(Model model) {
		
		return "/jpa_repository/home";
	}
	
	// ================================ CATEGORÍAS ===================================
	@GetMapping("/categorias")
	public String categorias(Model model) {
		
		// objetemos la liste de categorias y la enviamos con model a la vista
		model.addAttribute("datos", this.categoriaService.listarDescendente());
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
			return "/jpa_repository/categorias_add";
			
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
	
	// Método para eliminar una categoría
	@GetMapping("/categorias/delete/{id}")
	public String categorias_delete(@PathVariable("id") Integer id, RedirectAttributes flash) {
		// Lo cerramos en un try catch
		try {
			this.categoriaService.eliminar(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se eliminó el registro exitosamente.");
			return "redirect:/jpa-repository/categorias/";
		} catch (Exception e) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "No se pudo eliminar el registro, intentelo más tarde.");
			return "redirect:/jpa-repository/categorias/";
		}
	}
	
	// ================================ PRODUCTOS ===================================
	@GetMapping("/productos")
	public String productos(Model model) {
		
		// objetemos la liste de categorias y la enviamos con model a la vista
		model.addAttribute("datos", this.productoService.listarDescendente());
		return "/jpa_repository/productos";
	}
	
	// Buscar productos por categorías
	@GetMapping("/productos/categorias/{id}")
	public String productos_categorias(@PathVariable("id") Integer id, Model model) {
		// Obtenemos el producto
		CategoriaModel categoria = categoriaService.buscarPorId(id);
		
		// Verificamos si nos trae categorias y si no pues puede ser alguna inyección
		if(categoria == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Página no encontrada"); // NOT_FOUND nos retorna el error 404
		}
		
		model.addAttribute("datos", this.productoService.listarPorCategorias(categoria));
		model.addAttribute("categoria", categoria);
		return "/jpa_repository/productos_categorias";
	}
		
	// Métodos para crear nuevos productos
	@GetMapping("/productos/add")
	public String productos_add(Model model) {
		ProductoModel producto = new ProductoModel();
		model.addAttribute("producto", producto);
		return "/jpa_repository/productos_add";
	}
	
	// Recibimos los datos desde la vista con el PostMapping
	@PostMapping("/productos/add")
	public String productos_add_post(@Valid ProductoModel producto, BindingResult result, Model model
			, RedirectAttributes flash, @RequestParam("archivoImagen") MultipartFile multiPart) {
		// Verificamos el si tiene errores de validación
		// Validamos nuestro dato
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors()
			.forEach( err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			
			model.addAttribute("errores", errores);
			model.addAttribute("producto", producto);
			return "/jpa_repository/productos_add";
			
			}
		
		// Verificamos si no cargo ninguna imagen en el campo de la vista
		if (multiPart.isEmpty()) {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "El archivo para la imágen es obligatorio, debe ser JPG|JPEG|PNG");
			model.addAttribute("producto", producto);
			return "redirect:/jpa-repository/productos/add";
		}
		// Si cargó una imágen verificamos el mimetype
		if (!multiPart.isEmpty()) {
			// guardamos en una variable el resultado de nuestra utilidad que guarda la imágen
			// this.ruta_upload+"images" => ruta_upload es la variable de nuestra clase que almacena la ruta
			// que viene desde application.properties
			// this.ruta_upload+"images" => images será cualquier carpeta que creamos dentro de la carpeta de la ruta
			// Obligatorio poner la última barras images/
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload+"producto/");
			
			// Verificamos el valor de nombreImagen
			if (nombreImagen == "no") {
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la imágen no es válido, debe ser JPG|JPEG|PNG");
				model.addAttribute("producto", producto);
				return "redirect:/jpa-repository/productos/add";
			} 
			if (nombreImagen != null) {
				producto.setFoto(nombreImagen);
			}
		}
		
		producto.setSlug(Utilidades.getSlug(producto.getNombre()));
		this.productoService.guardar(producto);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se creó el registro exitosamente.");
		return "redirect:/jpa-repository/productos/add";
	}
	
	// Editar Producto
	@GetMapping("/productos/editar/{id}")
	public String productos_editar(@PathVariable("id") Integer id, Model model) {
		ProductoModel producto = productoService.buscarPorId(id);
		model.addAttribute("producto", producto);
		// producto.setCategoriaId(producto.getCategoriaId());
		return "/jpa_repository/productos_editar";
	}
	
	@PostMapping("/productos/editar/{id}")
	public String productos_editar_post(@Valid ProductoModel producto, BindingResult result, RedirectAttributes flash
			, @PathVariable("id") Integer id,  Model model, @RequestParam("archivoImagen") MultipartFile multiPart)  {
		
		// Preguntamos si existe errores en la validación
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors()
			.forEach( err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			
			model.addAttribute("errores", errores);
			model.addAttribute("producto", producto);
			return "/jpa_repository/productos_editar";
			
			}
		
		// Preguntamos si la imágen viene vacía
		if (!multiPart.isEmpty()) {
			String nombreImagen = Utilidades.guardarArchivo(multiPart, this.ruta_upload+"producto/");
			
			// Verificamos el valor de nombreImagen
			if (nombreImagen == "no") {
				flash.addFlashAttribute("clase", "danger");
				flash.addFlashAttribute("mensaje", "El archivo para la imágen no es válido, debe ser JPG|JPEG|PNG");
				return "redirect:/jpa-repository/productos/editar/"+id;
			} 
			if (nombreImagen != null) {
				producto.setFoto(nombreImagen);
			}
		}
		
		// Seteamos el slug
		producto.setSlug(Utilidades.getSlug(producto.getNombre()));
		this.productoService.guardar(producto);
		flash.addFlashAttribute("clase", "success");
		flash.addFlashAttribute("mensaje", "Se editó el registro exitosamente.");
		return "redirect:/jpa-repository/productos/editar/"+id;
	}
		
	// Eliminar un producto
	
	@GetMapping("/productos/delete/{id}")
	public String productos_delete(@PathVariable("id") Integer id, RedirectAttributes flash ) {
		// Obtenemos el objeto mediante el id
		ProductoModel producto = productoService.buscarPorId(id);
		// obtenemos la imágen desde el servidor para eliminarlo del mismo mientra eliminamos el registro
		File objImagen = new File(ruta_upload+"/producto/"+producto.getFoto());
		
		// Eliminamos mientras preguntamos si se eliminó o no
		if (objImagen.delete()) {
			this.productoService.eliminarPorId(id);
			flash.addFlashAttribute("clase", "success");
			flash.addFlashAttribute("mensaje", "Se eliminó el registro exitosamente.");
			
		} else {
			flash.addFlashAttribute("clase", "danger");
			flash.addFlashAttribute("mensaje", "No se pudo eliminar el registro intentelo más tarde.");
		}
		return "redirect:/jpa-repository/productos";
	}
	
	// ================================= WHERE IN =================================
	// Este where in es para filtrar por n cantidad de categorías que querramos filtrar en la lista
	@GetMapping("/productos-wherein")
	public String productos_wherein(Model model) {
		// Creamos la lista de categorías que queremos que se muestren en la lista de nuestro html de productos
		List<CategoriaModel> categorias = new ArrayList<CategoriaModel>();
		// añadimos las categorias a travez de los ids
		categorias.add(categoriaService.buscarPorId(1));
		categorias.add(categoriaService.buscarPorId(2));
		model.addAttribute("datos", this.productoService.listarConWhereIn(categorias));
		return "/jpa_repository/productos_wherein";
	}
	
	// ================================= CAMPOS GENÉRICOS ====================================
	// Añadimos al model la dirección Url que se encarga de accesar a la carpeta designada
	// de la subida de archivos.
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", this.categoriaService.listar());
		model.addAttribute("ruta_upload", this.base_url_upload);
	}
	
	
	
	
	
	
}
