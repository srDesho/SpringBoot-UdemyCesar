package com.cristianml.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.modelos.CategoriaModel;

//Por cada tabla o entidad crados en nuestros modelos, debemos crear una interface y un servicio
//desde el servicio vamos a ejecutar las consultas mediante la inyección de esta inteerface de comunicación
//que tiene JpaRepository


// Esta interface va a extender de JpaRepoitory<T, ID> done T es el modelo 
// y en ID debemos escribir el tipo de dato del id del modelo 
public interface ICategoriaRepositorio extends JpaRepository<CategoriaModel, Integer>{
	
	// Creamos un método que va a verificar si ua existe un slug
	// en el nombre debe ir como nomenclatura el existBy de lado izquierdo, seguido del nombre
	// del campo que queremos verificar.
	// Esto nos sirve para verificar si existe un sólo dato de nuestra tabla de bd.
	public boolean existsBySlug(String slug);
	
}
