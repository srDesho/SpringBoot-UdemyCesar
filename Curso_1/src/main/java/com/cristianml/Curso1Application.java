package com.cristianml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// Esta anotación señala que aquí se correrá todo el proyecto y está marcado como una app Spring Boot.
@SpringBootApplication
public class Curso1Application {

	public static void main(String[] args) {
		SpringApplication.run(Curso1Application.class, args);
	}
	
	// Creamos un bean para que el método pueda ser instanciado en cualquier parte del proyecto
	// Lo instancia con una inyección de dependencias y así nos evitamos estar creando los new RestTemplate
	// RestTemplate es un objeto que spring utiliza como clientes, sirve para consumir APIS
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
