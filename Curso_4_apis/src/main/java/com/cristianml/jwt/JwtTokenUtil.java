package com.cristianml.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.cristianml.models.UsuarioModel;
import com.cristianml.utilidades.Constantes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

// Esta clase es para configurar las utilidades de nuestro token.

@Component
public class JwtTokenUtil {
	
	private static final long EXPIRE_DURATION = 24*60*60*1000; // 24 horas
	
	// Creamos método para validar el token
	public boolean validateAccessToken(String token) {
		try {
			// Pasamos la firma y parseamos el token
			Jwts.parser().setSigningKey(Constantes.FIRMA).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			System.out.println("JWT Expirado " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.out.println("Token es null, está vacío o contiene espacios " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.out.println("JWT es inválido " + ex);
		} catch (UnsupportedJwtException ex) {
			System.out.println("JWT no soportado " + ex);
		} catch (SignatureException ex) {
			System.out.println("Validación de firma errónea.");
		}
		
		return false;
	}
	
	// Método para generar token
	public String generarToken(UsuarioModel usuario) {
		// Construimos el token
		return Jwts.builder()
				// El .setSubject es el que nos permite construir el payload (la seguna línea de código de nuestro token)
				.setSubject(String.format("%s,%s", usuario.getId(), usuario.getCorreo()))
				// Pasamos el valor asociado al protocolo OAuth
				.setIssuer("CristianML") // Escribimos lo que deseamos pasar
				// Pasamos otro parámetro para pasarle la fecha actual
				.setIssuedAt(new Date())
				// Pasamos parámetro para la expiración
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				// Pasamos el algoritmo de la firma y la firma
				.signWith(SignatureAlgorithm.HS512, Constantes.FIRMA)
				.compact(); // Para que todo se compacte.
	}
	
	// Método para manejar uno de los campos específicos que da problemas en la configuración de nuestro token. 
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims(String token) {
		// Formateamos el token que vamos a retornar
		return Jwts.parser()
				.setSigningKey(Constantes.FIRMA) // Esto es para pasarle la firma.
				.parseClaimsJws(token) // Pasamos el token.
				.getBody();
	}
}
