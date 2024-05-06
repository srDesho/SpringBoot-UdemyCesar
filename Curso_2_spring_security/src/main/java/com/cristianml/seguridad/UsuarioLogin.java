package com.cristianml.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cristianml.models.AutorizarModel;
import com.cristianml.models.UsuarioModel;
import com.cristianml.services.UsuarioService;

// La anotación @Component en Spring se utiliza para indicar que una clase es un componente de Spring 
// y debe ser automáticamente escaneada y registrada en el contexto de la aplicación. Esto significa que Spring administrará 
// y gestionará el ciclo de vida de esta clase, permitiendo la inyección de dependencias y otras funcionalidades.

//Como lo creamos como un component, automáticamente va a afectar al authenticatioManager de nuestra clase Seguridad
@Component

// El UserDetailsService sirve para dar la configuración el tipo de conexión de nuestro login
public class UsuarioLogin implements UserDetailsService {
	
	// Inyectamos el servicio de UsuarioService para poder implementar los datos y así podamos verificarlos en la base de datos
	@Autowired
	private UsuarioService usuarioService;

	// Implementamos el método recomendado el cual configuraremoos
	@Override
	@Transactional(readOnly = true) // importamos de org.springboot
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Creamos el usuario obtenido desde la base de datos
		UsuarioModel usuario = this.usuarioService.buscarPorCorreo(username, 1);
		// Verficamos si el usuario existe
		if (usuario == null) {
			throw new UsernameNotFoundException("El e-mail: " + username + " no existe en el sitema!");
		}
		
		// Configuramos los authorities
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); // Nos entrega todos los roles que tiene el usuario
		// Para poder recorrerlo lo hacemos con un for
		for(AutorizarModel role : usuario.getAutorizar()) {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		// Mostramos en pantalla
		System.out.println(authorities);
		
		// Preguntamos si authorities está vacío para saber si no tiene roles
		if(authorities.isEmpty()) {
			throw new UsernameNotFoundException("Error en el login: e-mail " + username + "no tiene roles asignados!");
		}
		
		// Retornamos el userDetail con los datos del usuario logeado
		// Usamos la clase User() de org.springframework.security para poder pasarle el nombre, contraseña,
		// los true son configuraciones adicionales depende a los que necesitamos y el último parámetro es el autority
		return new User(usuario.getNombre(), usuario.getPassword(), true, true, true, true, authorities);
	}

}
