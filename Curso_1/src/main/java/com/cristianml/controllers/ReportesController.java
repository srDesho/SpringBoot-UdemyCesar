package com.cristianml.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.cristianml.modelos.ProductoModel;
import com.cristianml.service.ProductoService;
import com.cristianml.utilidades.Utilidades;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;


@Controller
@RequestMapping("/reportes")
public class ReportesController {
	
	// Agregamos la ruta de nuestra aplicación 
		@Value("${cristian.valores.base.url_application}")
		private String base_url_application;
		
		@Value("${cristian.valores.base_url_upload}")
		private String base_url_upload;

	@GetMapping("")
	public String home() {
		return "/reportes/home";
	}
	
	// Para el excel creamos un atributo que será importado de poi 
	private XSSFWorkbook workbook;
	
	// ========================================= PDF ============================================
	
	// Creamos un atributo final, de tipo TemplateEngine que importamos de Thymeleaf que será es recurso que 
	// nos permitirá construir la renderización del archivo de la vista con la cuál vamos a trabajar.
	
	private final TemplateEngine templateEngine;
	
	// Creamos un constructor para configurar el templateEngine
	public ReportesController(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
		this.workbook = new XSSFWorkbook(); // Instanciamos nuestro workbook para el excel
	}
	
	// Inyectamos un ServletContext de java
	@Autowired
	private ServletContext servletContext;
	
	// Creamos el método para pdf, con el objeto ResponseEntity
	@GetMapping("/pdf")
	// El parámetro de tipo HttpServletRequest nos va a permitir recoger el request
	public ResponseEntity<?> productos_pdf(HttpServletRequest request, HttpServletResponse response) {
		// Creamos un objeto de tipo WebContext de Thymelef
		WebContext context = new WebContext(request, response, servletContext);
		// al objeto debemos setearle las siguientes variables
		// El setVariable nos permite pasar parámetros a la vista, trabaja como el addAtributes del Model
		context.setVariable("titulo", "PDF dinámico desde SpringBoot") ;
		context.setVariable("ruta", this.base_url_application);
		
		// Creamos la estructura de la ruta, spring ya sabe que la vista se encuentra en resources/template del proyecto
		// así que sólo debemos buscar nuestra carpeta y la vista -> "reportes/pdf"
		String html = this.templateEngine.process("reportes/pdf", context);
		
		// Creamos un objeto de tipo ByteArrayOutputStream de IO 
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		
		// Creamos un objeto de tipo ConverterProperties de itextpdf que instalamos en nuestro pom
		ConverterProperties converterProperties = new ConverterProperties();
		// seteamos la ruta de nuestro proyecto para que se configuren los recursos internamente
		converterProperties.setBaseUri(this.base_url_application);
		
		// Llamamos a la clase HtmlConverter.convertToPdf()
		// Le pasamos de parámetros nuestros objetos creados anteriormente
		HtmlConverter.convertToPdf(html, target, converterProperties);
		
		// Convertimos a un byte
		byte[] bytes = target.toByteArray();
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(bytes);
	}
	
	// ========================================= EXCEL ============================================

	// Inyectamos nuestro servicio de producto que trabaja con jpa-repository
	@Autowired
	private ProductoService productoService;
	
	// Creamos un atributo con el tipo XSSFSheet, para poder crear la filas del libro excel
	private XSSFSheet sheet;
	
	// Creamos nuestro método para controlar
	@GetMapping("/excel")
	public void excel(HttpServletResponse response) throws IOException {
		// Esto es para poder crear nuestro cabecero que utilizará el script para poder construir nuestro excel
		response.setContentType("application/octec-stream");
		
		// Asginamos un nombre de forma dinámica con el System.currentTimeMillis();
		long time = System.currentTimeMillis();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=reporte_" + time + ".xlsx";
	
		// Asignamos los headers(cabeceros de excel)
		response.setHeader(headerKey, headerValue);
		// header para evitar que las peticiones se tomen como peticiones iguales.
		response.setHeader("time", time+""); // la concatenación es para que el parámetro sea interpretado como string
		
		// Usamos el sheet para poder construir las filas de nuestro documento excel.
		// concatenamos el +time para que no se trabe a la hora de los nombres repetidos
		this.sheet = this.workbook.createSheet("Hola 1"+time); 
		
		// Creamos estilo para poder dar diseño a las celdas
		CellStyle style = this.workbook.createCellStyle();
		
		XSSFFont font = this.workbook.createFont();
		// font.setBold(true);
		// font.setFontHeight(16);
		style.setFont(font);
		
		// Creamos nuestros headers de la tabla excel
		Row row = this.sheet.createRow(0);
		createCell(row, 0, "ID", style);
		createCell(row, 1, "Nombre", style);
		createCell(row, 2, "Descripción", style);
		createCell(row, 3, "Precio", style);
		createCell(row, 4, "Foto", style);
		createCell(row, 5, "Time", style);
		
		// Generamos las filas dinámicas del reporte
		List<ProductoModel> datos = productoService.listar();
		
		// creamos un contador para que indique el lúgas de la fila
		int rowCount = 1;
		
		// iteramos generando los datos en excel
		for (ProductoModel dato : datos) {
			row = this.sheet.createRow(rowCount++);
			int columnCount = 0; // Contador para las columnas
			// Creamos las fila según la posición del columnCount
			createCell(row, columnCount++, dato.getId(), style);
			createCell(row, columnCount++, dato.getNombre(), style);
			createCell(row, columnCount++, dato.getDescripcion(), style);
			createCell(row, columnCount++, Utilidades.numberFormat(dato.getPrecio()), style);
			// Consejo: mejor no pasarle formateado para que los usuarios que descargue puedan trabajar a su manera en ofice excel
			createCell(row, columnCount++, this.base_url_upload+"producto/"+dato.getFoto(), style);
			createCell(row, columnCount++, time+"", style);
		}
		
		// Formulamos la salida
		ServletOutputStream outputStream = response.getOutputStream();
		this.workbook.write(outputStream);
		this.workbook.close();
		outputStream.close();
	}
	
	// Creamos un método que nos ayudará a crear las celdas de nuestro excel
	 
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer)value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
		} else {
			cell.setCellValue((String)value);
		}
		cell.setCellStyle(style);

	}
	
	
}
