package br.com.brmalls.xpto_client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( value = "/test" )
@CrossOrigin( "*" )
public class TestController {
	
	@GetMapping( value = "/message" )
	@ResponseBody
	public String message() {
		return "Mensagem de Teste!";
	}
}
