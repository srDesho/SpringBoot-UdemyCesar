package com.cristianml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.models.CategoriaModel;
import com.cristianml.models.ProductoModel;

public interface IProductoRepository extends JpaRepository<ProductoModel, Integer> {

	// Creamos método para comprobar si existe el id de una categoría relacionada con cualquiera de nuestros productos
	public boolean existsByCategoriaId(CategoriaModel categoria);
	
}
