package com.cristianml.utilidades;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

// Este paquete y clase nos va a permitir hacer más reutilizable la funcionalidad del upload,
// mediante los objetos de MultipartFile

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
				// Hacemos la subida (upload) con transferTo;
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
}
