package com.cristianml.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cristianml.models.CategoriaModel;
import com.cristianml.models.ProductoModel;
import com.cristianml.services.CategoriaService;
import com.cristianml.services.ProductoService;
import com.cristianml.utilidades.Constantes;
import com.cristianml.utilidades.Utilidades;

@RestController
@RequestMapping("/api/v1")
public class DbController {
	
	// Existe 2 tipos de retorno en nuestras apis:
	// 1. ResponseEntity = retorna todo lo que es transaccional, o sea que va a ejecutar alguna acción
	// como guardar, editar, eliminar.
	// 2. List<TipoDeDatoUModelo> = esto es para cuando queremos listar.
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ProductoService productoService;
	

	// ================================================ CATEGORIAS ================================================
	
	// Método listar
	@GetMapping("/categorias")
	public List<CategoriaModel> categorias() {
		return this.categoriaService.listar();
	}
	
	// Buscar por Id
	@GetMapping("/categorias/{id}")
	public CategoriaModel categorias_detalle(@PathVariable("id") Integer id) {
		return this.categoriaService.buscarPorId(id);
	}
	
	// Crear categoría
	
	// La anotación @RequestBody en Spring Boot se utiliza para indicar que un parámetro de un método controlador debe ser 
	// vinculado al cuerpo de la solicitud HTTP entrante. Esto es especialmente útil cuando se trabaja con solicitudes 
	// que envían datos en formato JSON, XML o cualquier otro formato que debe ser convertido a un objeto Java.

	// ¿Cómo funciona @RequestBody?
	// Cuando se recibe una solicitud HTTP que contiene datos en el cuerpo (por ejemplo, una solicitud POST con un JSON 
	// en el cuerpo), Spring MVC utiliza la anotación @RequestBody para:

	// Leer el cuerpo de la solicitud HTTP.
	// Convertir los datos del cuerpo al tipo de objeto especificado en el parámetro del método controlador.
	// La conversión se realiza utilizando HttpMessageConverters configurados (por ejemplo, MappingJackson2HttpMessageConverter
	// para JSON).
	
	@PostMapping("/categorias")
	// Va a retornar el tipo Object para que podamos utilizar nuestro response personalizado de nuestra clase Utilidades
	public ResponseEntity<Object> categorias_post(@RequestBody CategoriaModel request) {
		// Seteamos el slug para que no se ingrese vacío
		request.setSlug(Utilidades.getSlug(request.getNombre()));
		// Guardamos el registro
		this.categoriaService.guardar(request);
		// Retornamos la respuesta con el estado HttpStatus.CREATED que retorna el 201 que es el estandar de crear un registro
		return Utilidades.generateResponse(HttpStatus.CREATED, "Se ha creado el registro exitosamente");
	}
	
	// Método para editar
	@PutMapping("/categorias/{id}")
	public ResponseEntity<Object> categorias_put(@PathVariable("id") Integer id, @RequestBody CategoriaModel request) {
		// Obtenemos la categoria por id
		CategoriaModel categoria = this.categoriaService.buscarPorId(id);
		
		// Editamos los datos
		categoria.setNombre(request.getNombre());
		categoria.setSlug(Utilidades.getSlug(request.getNombre()));
		
		// Guardamos
		this.categoriaService.guardar(categoria);
		return Utilidades.generateResponse(HttpStatus.OK, "Se editó el registro exitosamente.");
	}
	
	// Método para eliminar una categoría
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<Object> categorias_delete(@PathVariable("id") Integer id) {
		System.out.println("Verificar ==== " + this.productoService.verificarRelacionCategoriaProducto(id));
		// Verificamos si la categoría tiene relación con registros de la tabla productos
		if (this.productoService.verificarRelacionCategoriaProducto(id) == false) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST
					, "No se puede eliminar el registro debido a que la categoría tiene relación con la tabla productos");
		}
		
		try {
			this.categoriaService.eliminar(id);
			return Utilidades.generateResponse(HttpStatus.OK, "Registro eliminado exitosamente.");
		} catch (Exception e) {
			// Retornamos BAD_REQUEST porque es el estado 400 que indica que hay una mala respuesta, en este caso
			// que hubo un error al borrar el registro
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "Falló la ejecución, por favor inténtelo más tarde.");
		}
	}
	
	
	// ================================================ PRODUCTOS ================================================
	@GetMapping("/productos")
	public List<ProductoModel> productos() {
		return this.productoService.listar();
	}
	
	// Detalle de un sólo producto
	@GetMapping("/productos/{id}")
	public ProductoModel productos_detalle(@PathVariable("id") Integer id) {
		return this.productoService.buscarPorId(id);
	}
	
	// Método para crear producto, subida de archivo
	// Para crearlo mediante Http debemos enviar a travez de un form-data no como un Json ya que vamos a
	// subir archivos a un servidor.
	// En postman tenemos la opción en body, marcamos form-data, desmarcamos en la pestaña Header el content-type
	// porque sino fallará, no debemos enviar el cabecero de tipo json al hacer uso de form-data.
	
	// En body escribimos los mismos campos que tenemos en nuestra base de datos, con sus valores
	// Y al final creamos el parámetro file, debe tener el mismo nombre que recibimos en nuestro  @RequestParam("file")
	// seleccionamos el tipo "file" y cargamos la imágen y enviamos
	
	@PostMapping("/productos")
	public ResponseEntity<Object> productos_post(ProductoModel producto, @RequestParam("file")
	MultipartFile file) {
		// Creamos variables de estado y mensaje
		HttpStatus status = HttpStatus.OK;
		String mensaje = "";
		
		// Validamos si el archivo es válido o si está vacío
		if (!file.isEmpty()) {
			// Creamos la imágen para que se suba a nuestro servidor
			String nombreImagen = Utilidades.guardarArchivo(file, Constantes.RUTA_UPLOAD+"producto/");
			// Validamos si la imágen es válida
			if (nombreImagen == "no") {
				status = HttpStatus.BAD_REQUEST;
				mensaje = "La foto enviada no es válida, debe ser de formato JPG|PNG|JPEG";
			} else {
				// Validamos que no sea nulo
				if (nombreImagen != null) {
					// Asignamos el nombre de la imágen al model
					producto.setFoto(nombreImagen);
					// Creamos el slug
					producto.setSlug(Utilidades.getSlug(producto.getNombre()));
					// Guardamos en la base de datos
					this.productoService.guardar(producto);
					
					status = HttpStatus.CREATED; // Asignamos el estado 2001 que se utiliza cuando se crea un registro
					mensaje = "Se creo el registro exitosamente.";
				}
			}
			
		} else {
			// Si está vacío significa que la imágen no se cargó o no es un archivo válido
			status = HttpStatus.BAD_REQUEST;
			mensaje = "La foto enviada no es válida, debe ser de formato JPG|PNG|JPEG";
		}
		
		return Utilidades.generateResponse(status, mensaje);
	}
	
}
