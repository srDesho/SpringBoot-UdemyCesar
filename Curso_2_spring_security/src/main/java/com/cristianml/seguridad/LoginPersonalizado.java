package com.cristianml.seguridad;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

// Esta clase es para intervenir el procedimiento del POST, podemos hacer muchas cosas para que haga luego de logearse el usuario.

// ÉSTA CLASE DEBE ESTAR INYECTADA EN LA CLASE Seguridad QUE ES DONDE TENEMOS CONFIGURADO NUESTRO CORE DE SEGURIDAD

@Component
// Heredamos de SimpleUrlAuthenticationSuccessHandler, para permitirnos agregar un filtro en el momento de hacer
// la petición del login
public class LoginPersonalizado extends SimpleUrlAuthenticationSuccessHandler{

	// Sobreescribimos el método onAuthenticationSuccess()
	@Override
	// Importamos FilterChain de javax.servlet
	// Importamos Authentication de org.springframework.security.core.Authentication
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain
			, Authentication authentication) throws IOException, ServletException {
		
		// Algunas de las cosas que podemos hacer:
		// Guardar un log cuando el usuario se logee.
		// Cuando el usuario esté logeado en páginas diferentes, se cierre las sesiones en todos lados.
		// Verificar si el usuario logeado tiene un pago para poder ver contenido restringido.
		// Inyectar un servicio que esté conectada a la base de datos
		// Muchas cosas más...
		
		
		
		// Podemos redireccionar a donde querramos
		response.sendRedirect("/");
		
		super.onAuthenticationSuccess(request, response, chain, authentication);
	}
	
}
