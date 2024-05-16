package com.cristianml.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cristianml.models.CategoriaModel;
import com.cristianml.repositories.ICategoriaRepository;

@Service
@Primary
public class CategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	// Listar
	public List<CategoriaModel> listar() {
		return this.categoriaRepository.findAll(Sort.by("Id").descending());
	}
	
	// Guardar
	public void guardar(CategoriaModel categoria) {
		this.categoriaRepository.save(categoria);
	}
	
	// Buscar por Id
	public CategoriaModel buscarPorId(Integer id) {
		// Creamos un objeto optional para que springboot pueda verificar si el dato existe
		Optional<CategoriaModel> optional = this.categoriaRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// Eliminar por id
	public void eliminar(Integer id) {
		this.categoriaRepository.deleteById(id);
	}
	
}
