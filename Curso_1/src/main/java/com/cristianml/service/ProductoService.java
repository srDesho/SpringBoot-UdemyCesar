package com.cristianml.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cristianml.modelos.ProductoModel;
import com.cristianml.repositorios.IProductoRepositorio;

@Service
@Primary
public class ProductoService {
	// Por cada tabla o entidad crados en nuestros modelos, debemos crear una interface y un servicio
	// desde el servicio vamos a ejecutar las consultas mediante la inyección de esta interface de comunicación
	// que tiene JpaRepository

	// Lo primero que tenemos que hacer es inyectar Interface con la que vamos a envolver este servicio
	
	@Autowired
	private IProductoRepositorio repositorio;
	
	// Creamos los métodos de altas, bajas, lectura y modificaciones
	
	// Obtener productos
	public List<ProductoModel> listar() {
		return repositorio.findAll();
	}
	
	public List<ProductoModel> listarDescendente() {
		return repositorio.findAll(Sort.by("id").descending());
	}

	// Guardar producto
	public void guardar(ProductoModel producto) {
		this.repositorio.save(producto);
	}
	
	// Buscar producto por id
	public ProductoModel buscarPorId(Integer id) {
		Optional<ProductoModel> optional = repositorio.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// Eliminar por Id
	public void eliminarPorId(Integer id) {
		repositorio.deleteById(id);
	}

}
