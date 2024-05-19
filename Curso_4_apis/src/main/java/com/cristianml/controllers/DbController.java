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
import org.springframework.web.bind.annotation.RestController;

import com.cristianml.models.CategoriaModel;
import com.cristianml.services.CategoriaService;
import com.cristianml.services.ProductoService;
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
	
	
}
