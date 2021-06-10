package br.com.brmalls.xpto_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.brmalls.xpto_client.models.CompanyModel;
import br.com.brmalls.xpto_client.services.CompanyService;

@Controller
@RequestMapping( value = "/company" )
@CrossOrigin( "*" )
public class CompanyController {
	
	@PostMapping( value = "/" )
	@ResponseBody
	public CompanyModel create( @RequestBody final CompanyModel company ) {
		
		this.service.create( company );
		
		return company;
		
	}
	
	@Autowired
	private CompanyService service;
	
}
