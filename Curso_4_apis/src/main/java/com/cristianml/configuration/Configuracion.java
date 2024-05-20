package com.cristianml.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cristianml.utilidades.Constantes;

// Esta clase es para hacer configuraciones, ejemplo ahora para hacer la subida de archivos
@Configuration
public class Configuracion implements WebMvcConfigurer {

	// sobreescribimos el siguiente método para configurar la ruta de nuestra carpeta upload (o un servidor).
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Agregamos la ruta
		registry.addResourceHandler("/upload/**").addResourceLocations("file: "
				+ Constantes.RUTA_UPLOAD);
	}
	
}
