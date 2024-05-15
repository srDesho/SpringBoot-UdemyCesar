package com.cristianml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.models.UsuarioModel;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
	
	// Método para que nos permita buscar un usuario por el correo
	public UsuarioModel findByCorreo(String correo);
}
