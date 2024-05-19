package com.cristianml.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cristianml.models.CategoriaModel;
import com.cristianml.models.ProductoModel;
import com.cristianml.repositories.IProductoRepository;

@Service
@Primary
public class ProductoService {
	@Autowired
	private IProductoRepository productoRepository;
	
	// Listar
	public List<ProductoModel> listar() {
		return this.productoRepository.findAll(Sort.by("id").descending());
	}
	
	// Guardar, editar
	public void guardar(ProductoModel producto) {
		this.productoRepository.save(producto);
	}
	
	// Buscar por id
	public ProductoModel buscarPorId(Integer id) {
		// Creamos un objeto optional para que springboot pueda verificar si el dato existe
		Optional<ProductoModel> optional = this.productoRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// Eliminar
	public void eliminar(Integer id) {
		this.productoRepository.deleteById(id);
	}

	// Método para saber si existe una relación de una categoría con productos
	public boolean verificarRelacionCategoriaProducto(Integer categoriaId) {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setId(categoriaId);
        if (productoRepository.existsByCategoriaId(categoria)) {
        	return false;
        } else {
        	return true;
        }
    }
	
}
