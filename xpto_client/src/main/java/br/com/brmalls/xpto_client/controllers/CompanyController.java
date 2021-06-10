package br.com.brmalls.xpto_client.controllers;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.brmalls.xpto_client.dtos.CompanyDTO;
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
	
	@GetMapping( value = "/cnpj/{cnpj}" )
	@ResponseBody
	public Map<String, String> get( @PathVariable( value = "cnpj" ) final String cnpj ) {
		
		final Map<String, String> result = new HashMap<String, String>();
		
		final CompanyDTO dto = this.service.getCompany( cnpj );
		
		if( dto.getFantasyName().isEmpty() && dto.getSocialName().isEmpty() ) {
			// ---
		}
		else
		{
			result.put( "fantasyName", dto.getFantasyName() );
			result.put( "socialName", dto.getSocialName() );
		}
		
		return result;
	}
	
	@Autowired
	private CompanyService service;
	
}
