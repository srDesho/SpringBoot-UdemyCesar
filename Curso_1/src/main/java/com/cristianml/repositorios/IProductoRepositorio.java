package com.cristianml.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.modelos.ProductoModel;

// Por cada tabla o entidad crados en nuestros modelos, debemos crear una interface y un servicio
// desde el servicio vamos a ejecutar las consultas mediante la inyección de esta inteerface de comunicación
// que tiene JpaRepository

//Esta interface va a extender de JpaRepoitory<T, ID> done T es el modelo 
//y en ID debemos escribir el tipo de dato del id del modelo 
public interface IProductoRepositorio extends JpaRepository<ProductoModel, Integer>{
	
}
