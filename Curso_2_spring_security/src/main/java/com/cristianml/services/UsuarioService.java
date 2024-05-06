package com.cristianml.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cristianml.models.UsuarioModel;
import com.cristianml.repositorios.IUsuarioRepository;

@Service
@Primary
public class UsuarioService {
	
	@Autowired
	private IUsuarioRepository repository;
	
	// Creamos método para obtener el último Id del objeto que acabamos de agregar, el id lo vamos a necesitar para despues
	// en el registro del usuario vamos a crear el usuario y con el mismo objeto vamos a crear el registro en la tabla de
	// autorización.
	public UsuarioModel guardar(UsuarioModel entity) {
		return repository.save(entity);
	}
	
	// Buscar por correo y si está activo
	public UsuarioModel buscarPorCorreo(String correo, Integer estado) {
		return repository.findByCorreoAndEstado(correo, estado);
	}
	
}
