package com.cristianml.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/liberado")
public class LiberadoController {

	@GetMapping("")
	public String home() {
		return "/liberado/home";
	}
	
	@GetMapping("/liberado-2")
	public String liberado_2() {
		return "/liberado/liberado_2";
	}

}
