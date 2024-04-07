package com.cristianml.componentes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cristianml.modelos.InteresesModel;

// Vamos a usar éste componente para almacenar los id de los checkbox y poder setearlos al objeto
// para luego poder usar los id
@Component
public class ConvertirInteresesId implements Converter<String, InteresesModel>{

	@Override
	public InteresesModel convert(String source) {
		Integer idInteresesModel = Integer.valueOf(source);
		InteresesModel interes = new InteresesModel();
		interes.setId(idInteresesModel);
		return interes;
	}

	
	
}
