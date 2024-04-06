package com.cristianml.componentes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cristianml.modelos.PaisModel;

// Vamos a usar éste componente para convertir el valor id de PaisModel de forma dinámica (convertir pais a un objeto)
// también se usa para hacer configuraciones, para crear servicios, para crear elementos de carga masivo
@Component
public class ConvertirPaisId implements Converter<String, PaisModel> {

	@Override
	public PaisModel convert(String source) {
		Integer idPais = Integer.valueOf(source);
		PaisModel datos = new PaisModel();
		datos.setIdPais(idPais);
		return datos;
	}

}
