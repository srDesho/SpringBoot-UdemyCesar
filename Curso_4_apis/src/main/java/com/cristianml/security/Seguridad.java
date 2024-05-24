package com.cristianml.security;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cristianml.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad {
	
	// Inyectamos nuestro filtro creado en la clase JwtTokenFilter
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	// Creamos el método que va a procesar toda la autenticación que va a esperar el spring security,
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
	throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// Creamos el BCrypt para el manejo y administración de las contraseñas
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Creamos método para configurar el SecurityFilterChain
	// Para hacer la prueba en postman debemos:
	// Obtener el token con el controlador de AccesoController (buscar en chatGpt "como obtengo un token en postman con una api ya creada")
	// Pasamos los cabeceros Content-Type	application/json y Authorization	Bearer aquíPegarElTokenCreado
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		/* Esta es una configuración sencilla
		 
		// Deshabilitamos el csrf
		http.csrf().disable();
		// Para que acepte todas las peticiones
		http.authorizeRequests().anyRequest().permitAll();
		// Decimos que va a trabajar con una política de creación estándar
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build(); 
		*/
		
		// Hacemos la configuración para asegurar las rutas
		
		// Deshabilitamos el csrf
		http.csrf().disable();
		// Decimos que va a trabajar con una política de creación estándar
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// Con lo siguiente le decimos que va a hacer una configuración adicional
		http.exceptionHandling()
		.authenticationEntryPoint( (request, response, ex) -> {
			// Enviamos el error si es que lo hay.
			// UNAUTHORIZED es el error 401 el cual se refiere a que no está autenticado
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		});
		
		// Cargamos el filtro que tenemos creado
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		// Configuramos la ruta del login
		http.authorizeHttpRequests()
		// En este ejemplo esta ruta no tiene que pasar por el filtro, pero todas las demás sí.
		.antMatchers("api/v1/login")
		.permitAll()
		.anyRequest()
		.authenticated();
		
		return http.build(); 
	}
	
	
	
}
