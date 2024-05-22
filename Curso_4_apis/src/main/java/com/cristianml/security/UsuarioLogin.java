package com.cristianml.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cristianml.models.UsuarioModel;
import com.cristianml.services.UsuarioService;

// Esta clase va a ser para crear todas las funcionalidades para crear una ruta que permita retornar
// un token válido y poder proteger utilizarlo para proteger las URL

@Component
public class UsuarioLogin implements UserDetailsService {
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UsuarioModel usuario = this.usuarioService.findByCorreo(username); 
		if (usuario == null) {
			throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// Importamos User de UserDetail
		return new User(usuario.getCorreo(), usuario.getPassword(), true, true, true, true, authorities);
	}
	
	
	
}
