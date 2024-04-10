package com.cristianml.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cristianml.modelos.ProductosRestModel;

// Esta clase nos va a permitir ejecutar nuestro cliente rest

@Service
@Primary
public class ClienteRestService {
	
	// Creamos un atributo de tipo RestTemplate que creamos como @bean en nuestra clase Curso1Application
	// obligatorio que estpe creado el método porque sino no se podrá llamar el objeto RestTemplate
	@Autowired
	private RestTemplate clienteRest;
	
	// Llamamos nuestra ruta de la api
	@Value("${cristian.valores.api.host.baseurl}")
	private String apiHost;
	
	// En este constructor inicializamos el cliente rest con RestTemplateBuilder
	public ClienteRestService(RestTemplateBuilder builder) {
		this.clienteRest = builder.build();
	}
	
	// Creamos un método privado que nos va a retornar los cabeceros desde postman con HttpHeaders
	private HttpHeaders setHeaders() {
		HttpHeaders headers = new HttpHeaders();
		// Pasamos el cabezero que es para la carga de datos vía APPLICATION_JSON
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Bearer con un token válido");
		return headers;
	}
	
	// Creamos un método que nos va a devolver la lista de ProductosRestModel
	public List<ProductosRestModel> listar() {
		// Configuramos los encabezado con el método creado anteriormente
		HttpEntity<String> entity = new HttpEntity<String>(this.setHeaders());
		
		// Creamos un valor de tipo ResponseEntity con el cuál vamos a hacer la petición a nuestro endpoint
		ResponseEntity<List<ProductosRestModel>> response = this.clienteRest.exchange(apiHost+"productos"
				, HttpMethod.GET, entity, new ParameterizedTypeReference<List<ProductosRestModel>>() {});
		
		// .getBody nos trae la respuesta que llega con una lista del modelo que que pasamos
		return response.getBody();
	}
	
	
	
	
	
	
}
