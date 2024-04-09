package com.cristianml.service;


import java.util.Date;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.cristianml.utilidades.Constantes;

// Este servicio nos servirá para hacer envios de correos electrónicos, debemos tener implementado
// la dependencia en para enviar email

@Service
@Primary
public class EmailService {
	
	// Creamos 4 constantes en una clase de paquete utilidades llamada Constantes los cuales almacenarán 
	// los datos SMTP necesarios y deben de ser staticos y finales (constantes)
	
	// Creamos el método que nos permitirá hacer el envío de correo
	public void sendEmail(String mail, String asunto, String mensaje) throws AddressException, MessagingException {
		// Con Properties hacemos la configuración de las propiedades que se van a ejecutar para el enVío SMTP
		Properties props = new Properties();
		// Pasamos valores al props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Constantes.SMTP_SERVER);
		props.put("mail.smtp.port", Constantes.SMTP_PORT);
		
		// Creamos objeto de tipo session
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constantes.SMTP_MAIL, Constantes.SMTP_PASSWORD);
			}
		});
		
		// Creamos objeto Message que enviará el mensaje a travez del objeto InternetAddress
		Message msg = new MimeMessage(session);
		
		// Enviamos mensaje con setFrom
		msg.setFrom(new InternetAddress( Constantes.SMTP_MAIL, false));
		// Indicamos quién va a recibir el correo
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		// Añadimos el asunto
		msg.setSubject(asunto);
		// Pasamos el formato del correo
		msg.setContent(mensaje, "text/html");
		// Seteamos date para evitar el spam, en 0 para que se entienda que es una fecha válida
		msg.setSentDate(new Date(0));
		
		// ejecutamos el mimetype del envío
		// Convierte a un  mime para que sea válido el envío
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		// Recibe el mensaje que se trabajará
		messageBodyPart.setContent(mensaje, "text/html");
		
		// Ejecutamos un transport para que se envíe el mensaje
		Transport.send(msg);
		
	}
}
