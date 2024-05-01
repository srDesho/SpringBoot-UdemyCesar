package com.cristianml.seguridad;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// Esta clase la vamos a usar para configurar el core del framework

@Configuration
// Con esta anotación le habilitamos todo el paquete del batería para trabajar con spring security
@EnableWebSecurity
// Con esto spring framework entienda que aquí vamos a hacer las configuraciones manualmente, nuestra propia configuración
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad {

}
