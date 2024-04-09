package com.cristianml.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

// Utilizamos services porque es una de las formas mas utilizadas para hacer inyección de dependencias

@Service // Nos ayuda a que este servicio sea inyectado en otra lado del proyecto
@Primary // Usamos @Primary por si existe otro servicio con el mismo nombre tome éste como principal.
public class EjemploService {

	public String saludo() {
		return "Hola desde un servicio inyectado desde Spring.";
	}
	
}
