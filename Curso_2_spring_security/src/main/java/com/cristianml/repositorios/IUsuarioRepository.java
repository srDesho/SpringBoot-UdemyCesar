package com.cristianml.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cristianml.models.UsuarioModel;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer>{
	
	// SELECT * FROM usuarios WHERE correo = 'algo@gmail.com' AND estado = 1
	public UsuarioModel findByCorreoAndEstado(String correo, Integer estado);
}
