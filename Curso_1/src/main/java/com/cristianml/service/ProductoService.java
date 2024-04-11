package com.cristianml.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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

}
