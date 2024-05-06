package com.cristianml.seguridad;

import javax.annotation.security.PermitAll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Esta clase la vamos a usar para configurar el core del framework

@Configuration
// Con esta anotación le habilitamos todo el paquete del batería para trabajar con spring security
@EnableWebSecurity
// Con esto spring framework entienda que aquí vamos a hacer las configuraciones manualmente, nuestra propia configuración
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad {

	// Creamos los métodos que spring security utilizará para hacer todas las configuraciones, sobre todo para autenticación
	
	// La anotación @Bean en Spring Boot se utiliza para indicarle a Spring que un método específico en una clase de configuración 
	// debe ser tratado como un método de inicialización para construir y configurar un objeto gestionado por Spring en el 
	// contenedor de Spring (ApplicationContext).
	// En resumen, @Bean se utiliza para definir métodos que producen beans administrados por Spring.
	// Esto significa que Spring manejará el ciclo de vida y la configuración de estos objetos.
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// Creamos el método para que podamos implementar las contraseñas encriptadas de los usuarios
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Creamos otro método para poder configurar nuestro filtros con SecurityFilterChain
	// el paráetro http es el que vamos a configurar para poder realizar los filtros
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// authorizeHttpRequests() nos permite hacer las configuraciones
		http.authorizeHttpRequests()
		
		// las vistas que no van a tener protección, o sea que serán públicas para que el usuario pueda visualizar
		.antMatchers(
				"/",
				"/liberado/**", // doble asterisco para que todo que esté adelante de la ruta liberada no tenga protección
				"/acceso/registro"
				).permitAll() // este método permite la configuración solo para lo que está dentro del paréntesis
		
		// asignar permisos a URLs por ROLES
		.antMatchers(
				"/protegido/**"
				// Con este método le indicamos los roles que queremos que accesen a las rutas que escribimos
				).hasAnyAuthority("ROLE_ADMIN")
				// .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
		
		// Ahora toca hacer las configuraciones generales
		.anyRequest().authenticated() // para decirle al usuario que se va a autenticar con esto
		
		// Indicamos el formato del login
		.and().formLogin().permitAll()
		// Lo mismo hacemos con el logout
		.and().logout().permitAll()
				;
		
		// el .build() es el que tiene toda la información que nosotros deseemos ejecutar
		return http.build();
	}
	
	// Configuración para poder mostrar los objetos estáticos (imágenes, videos, archivos js, css, etc)
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer () {
		// Automáticamente se refiere a la carpeta resources de nuestro proyecto
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/css/**");
	}
	
}
