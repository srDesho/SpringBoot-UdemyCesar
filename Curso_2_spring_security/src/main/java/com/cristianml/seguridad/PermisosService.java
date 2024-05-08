package com.cristianml.seguridad;

import java.util.Collection;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

// Este servicio va a ser para comprobar si los usuarios tienen roles

@Service
@Primary
public class PermisosService {

	public boolean comprobarRol(String rol) {
		// Instanciamos objeto de tipo SecurityContext que importamos de spring security
		SecurityContext context = SecurityContextHolder.getContext();
		
		// Preguntamos si es null para ver si existe el context
		if (context == null) {
			return false; // Significa que no hay nadie logeado o que el usuario no tiene roles asignados
		}
		// Revisamos la autenticación del usuario con
		// Authentication que importamos de springframework.security.core
		Authentication auth = context.getAuthentication();
		
		// Preguntamos si el auth es null
		if (auth == null) {
			return false; // Significa que no hay nadie logeado o que el usuario no tiene roles asignados
		}
		
		// Obtenemos todos los roles en una Collection
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(rol));
	}
	
}
