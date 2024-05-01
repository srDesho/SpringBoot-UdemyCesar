package com.cristianml.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// Esta clase la vamos a usar para configurar el core del framework

@Configuration
// Con esta anotación le habilitamos todo el paquete del batería para trabajar con spring security
@EnableWebSecurity
// Con esto spring framework entienda que aquí vamos a hacer las configuraciones manualmente, nuestra propia configuración
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad {

	// Creamos el método que spring security utilizará para hacer todas las configuraciones, sobre todo para autenticación
	
	// La anotación @Bean en Spring Boot se utiliza para indicarle a Spring que un método específico en una clase de configuración 
	// debe ser tratado como un método de inicialización para construir y configurar un objeto gestionado por Spring en el 
	// contenedor de Spring (ApplicationContext).
	// En resumen, @Bean se utiliza para definir métodos que producen beans administrados por Spring.
	// Esto significa que Spring manejará el ciclo de vida y la configuración de estos objetos.
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
