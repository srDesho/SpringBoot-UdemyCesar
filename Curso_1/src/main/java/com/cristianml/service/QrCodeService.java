package com.cristianml.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
@Primary
public class QrCodeService {
	
	// Antes debemos agregar las dependecias de google.zxing, el de "core" y el de "javase"

	// Creamos el método para generar qr
	public byte[] crearQR(String text, int width, int height) throws WriterException, IOException {
		
		// Instanciamos el QRCodeWriter 
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		
		// BitMatrix codifica toda la construcción del formato para qr con el método .encode
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		
		// objeto para usar la librería para que retorne cualquiera de los formatos que necesitemos trabajar
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		
		// objeto para dar color a nuestro qr
		MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0x00000000);
		
		// esto nos va configurar para que los objetos creados anteriormente puedan trabajar juntos
		// en el segundo parámetro agregamos el tipo de imágen que queremos que retorne nuestro qr
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
		
		// Variable para almacenar nuestro objeto convertido en una imágen con el método .toByteArray
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData; // Retornamos la imágen
	}
	
}
