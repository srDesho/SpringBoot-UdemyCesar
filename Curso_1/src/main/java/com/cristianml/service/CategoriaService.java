package com.cristianml.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cristianml.modelos.CategoriaModel;
import com.cristianml.repositorios.ICategoriaRepositorio;

@Service
@Primary
public class CategoriaService {
	// Por cada tabla o entidad crados en nuestros modelos, debemos crear una interface y un servicio
	// desde el servicio vamos a ejecutar las consultas mediante la inyección de esta interface de comunicación
	// que tiene JpaRepository

	// Lo primero que tenemos que hacer es inyectar Interface con la que vamos a envolver este servicio
	
	@Autowired
	private ICategoriaRepositorio repositorio;
	
	// Creamos los métodos de altas, bajas, lectura y modificaciones
	
	// Obtener categorias
	public List<CategoriaModel> listar() {
		return repositorio.findAll();
	}
	
	// Listar por orden descendente
	public List<CategoriaModel> listarDescendente() {
		return repositorio.findAll(Sort.by("id").descending());
	}
	
	// Crear categoria
	public void guardar(CategoriaModel categoria) {
		repositorio.save(categoria);
	}
	
	// Eliminar una categoría
	public void eliminar(Integer id) {
		repositorio.deleteById(id);
	}
	
	
	// Buscar categoría por id
	public CategoriaModel buscarPorId(Integer id) {
		// Creamos un objeto optional para que springboot pueda verificar si el dato existe
		Optional<CategoriaModel> optional = this.repositorio.findById(id);
		// Verificamos si existe
		if (optional.isPresent()) {
			return optional.get(); // Con ésto obtenemos y devolvemos el objeto CategoriaModel
		}
		return null;
	}
	
	
	// Buscar por slug
	// éste méotodo nos va a verificar si un slug ya existe en la base de datos, 
	// el método debemos crearlo en la ICategoriaRepository
	public boolean buscarPorSlug(String slug) {
		if (repositorio.existsBySlug(slug)) {
			return false; // Si existe no se permitirá continuar con el proyecto para crear un nuevo dato
		} else {
			return true;
		}
	}
	
	
	
}
