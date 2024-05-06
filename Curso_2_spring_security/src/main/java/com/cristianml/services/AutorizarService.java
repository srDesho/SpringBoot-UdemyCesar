package com.cristianml.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cristianml.models.AutorizarModel;
import com.cristianml.repositorios.IAutorizar;

@Service
@Primary
public class AutorizarService {
	
	@Autowired
	private IAutorizar repository;
	
	// Guardar
	public void guardar(AutorizarModel autorizar) {
		repository.save(autorizar);
	}
	
}
