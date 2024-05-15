package com.cristianml.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cristianml.models.UsuarioModel;
import com.cristianml.repositories.IUsuarioRepository;

@Service
@Primary
public class UsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	// Guardar
	public void guardar(UsuarioModel usuario) {
		this.usuarioRepository.save(usuario);
	}
	
	// Buscar usuario por correo
	public UsuarioModel findByCorreo(String correo) {
		return this.usuarioRepository.findByCorreo(correo);
	}
	
}
