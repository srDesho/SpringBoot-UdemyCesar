package com.cristianml.utilidades;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class Utilidades {
	// Creamos los métodos estáticos
		// ruta nos sirve para que trabaje de forma dinámica la ruta
		public static String guardarArchivo(MultipartFile multiPart, String ruta) {
			// Verificamos la extensión del archivo, con getContentType ingresamos al mime y nos retorna el tipo en string
			// Si es falsa la validación entonces no guarda
			if (Utilidades.validarImagen(multiPart.getContentType()) == false) {
				return "no";
			} else {
				// Si es verdadera entonces guardamos
				// Creamos la variable time y nombre para que cree un nombre único a nuestros archivos
				long time = System.currentTimeMillis(); // currentTimeMillis() nos da el tiempo actual en milisegundos
				String nombre = time + Utilidades.getExtension(multiPart.getContentType());
				try {
					// Creamos el archivo(objeto) con la clase File de IO
					File imageFile = new File(ruta+nombre); // Recibe la ruta de la carpeta y el nombre agregado
					// Hacemos la subida (upload) con transferTo aquí es donde se crea el archivo imágen;
					multiPart.transferTo(imageFile);
					return nombre;
				} catch(IOException e) {
					return null;
				}	
			}
			
			
		}
		
		// La variable mime nos va a servir para verificar el tipo de archivo para poder analizarlo e igualar 
		// al archivo que necesitamos que sean cargados y así evitamos que nos inyecten otro archivo malicioso
		public static boolean validarImagen(String mime) {
			boolean retorno = false;
			
			// Usamos el switch para comparar con varios tipo de archivos
			switch(mime) {
				case "image/jpeg":
					retorno = true;
				break;
				case "image/jpg":
					retorno = true;
				break;
				case "image/png":
					retorno = true;
				break;
				default:
					retorno = false;
					break;
			}
			return retorno;
		}
		
		// Creamos un método para agregar y devolver el tipo de extensión
		public static String getExtension(String mime) {
			String retorno = "";
			// Usamos el switch para comparar con varios tipo de archivos
			switch(mime) {
				case "image/jpeg":
					retorno = ".jpeg";
				break;
				case "image/jpg":
					retorno = ".jpg";
				break;
				case "image/png":
					retorno = ".png";
				break;
			}
			return retorno;
		}
		
		// slug
		// En un proyecto Java, un "slug" es una cadena de texto que se utiliza para identificar de forma única
		// un recurso o entidad dentro de una aplicación web. Generalmente, los slugs se utilizan en URL 
		// amigables para mejorar la legibilidad y la SEO (optimización para motores de búsqueda) de los enlaces.
		
		// Esto nos va a servir para formatear y construir el slug que vamos a necesitar
		private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
		private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
		private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

		// Método para el slug
		// Convierte la cadena de texto en un slug
		public static String getSlug(String input) {
			String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
			String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
			String slug = NONLATIN.matcher(normalized).replaceAll("");
			slug = EDGESDHASHES.matcher(slug).replaceAll("");
			return slug.toLowerCase(Locale.ENGLISH);
		}
		
		// Creamos nuestro método para formatear nuestro ResponseEntity, o sea para que esté personalizado
		// de tipo object para que pueda recibir cualquier tipo, le ponemos el nombre que querramos
		// le pasamos el parámetro HttpStatus para indicar el estado Http que va a retornar
		public static ResponseEntity<Object> generateResponse(HttpStatus status, String mensaje) {
			// Creamos un Map para indicar la estructura del json
			Map<String, Object> map = new HashMap<String, Object>();
			
			// Envolvemos con un try catch
			try {
				
				map.put("fecha", new Date());
				map.put("status", status);
				map.put("mensaje", mensaje);
				// Podemos agregar muchos mas valores que necesitemos al map.
				
				return new ResponseEntity<Object>(map, status);
				
			} catch (Exception e) {
				// Si hay error pues hacemos lo siquiente
				map.clear(); // Limpiamos el map, por si se cargó algo antes del error
				map.put("fecha", new Date());
				map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				map.put("mensaje", e.getMessage());
				// Podemos agregar muchos mas valores que necesitemos al map.
				
				return new ResponseEntity<Object>(map, status);
			}
		}
		
}
