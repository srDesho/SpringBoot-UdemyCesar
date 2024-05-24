package com.cristianml.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristianml.jwt.AuthRequest;
import com.cristianml.jwt.AuthResponse;
import com.cristianml.jwt.JwtTokenUtil;
import com.cristianml.models.UsuarioModel;
import com.cristianml.services.UsuarioService;

@RestController
@RequestMapping("/api/v1")
public class AccesoController {
	
	// Inyectamos AuthenticationManager de org.springframework.security
	@Autowired
	private AuthenticationManager authManager;
	
	// Inyectamos nuestro componente creado JwtTokenUtil
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	// Inyectamos nuestro servicio Jpa
	@Autowired
	private UsuarioService usuarioService;
	
	// Creamos un método post que retornará un json
	@PostMapping("/login")
	// ResponseEntity<?> con una incognita porque no vamos a necesitar nada.
	// Vamos a usar RequestBody porque nos permite retornar parámetros Json Request.
	// {"correo":"info@tamila.cl", "password":"123456"}
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		
		try {
			// .authenticate() es el que va a hacer la implementación de la configuración de la autenticación del usuario.
			// Esto nos va a ayudar a obtener el password y todo para enviarselo a springboot internamente.
			// También spring encripta la contraseña internamente con el método ByCrypt que creamos en nuestra clase Seguridad.
			// Authentication debe ser importado de security.core
			Authentication authentication = this.authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
			
			// Buscamos los datos de los usuarios por el correo para estar más seguros y usarlo para construir el token.
			UsuarioModel usuario = this.usuarioService.findByCorreo(request.getCorreo());
			
			// Construimos el token
			String accessToken = this.jwtUtil.generarToken(usuario);
			
			// Creamos objeto de tipo AuthResponse, es nuestro model que creamos.
			AuthResponse response = new AuthResponse(request.getCorreo(), accessToken);
			
			return ResponseEntity.ok(response);
			
		} catch (BadCredentialsException e) {
			// UNAUTHORIZED es el error 401 el cual se refiere a que no está autenticado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
		}
	}
	
}
