package com.cristianml.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.modelos.CategoriaModel;
import com.cristianml.modelos.ProductoModel;

// Por cada tabla o entidad crados en nuestros modelos, debemos crear una interface y un servicio
// desde el servicio vamos a ejecutar las consultas mediante la inyección de esta inteerface de comunicación
// que tiene JpaRepository

//Esta interface va a extender de JpaRepoitory<T, ID> done T es el modelo 
//y en ID debemos escribir el tipo de dato del id del modelo 
public interface IProductoRepositorio extends JpaRepository<ProductoModel, Integer>{
	
	// Creamos una busqueda compuesta al igual que hicimos con el slug en las categorías
	// Creamos un List<tipo_de_objeto> con la nomenclatura findAllBy seguido del campo que quremos consultar de nuestro modelo
	// recibe como parámetro el filtro que queremos hacer
	List<ProductoModel> findAllByCategoriaId(CategoriaModel categoria);
	
	// Puede ser tambien si queremos que filtre por el nombre de la categoría
	// List<ProductoModel> findAllByCategoriaIdNombre(CategoriaModel categoria, String nombre);
	
	// Para poder hacer un where in
	// Hacemos los mismo que antes sólo que añadimos el in al final y el parámetro va a ser una lista del tipo del modelo requerido
	List<ProductoModel> findAllByCategoriaIdIn(List<CategoriaModel> categorias);
}
