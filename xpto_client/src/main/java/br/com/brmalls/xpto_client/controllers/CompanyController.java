package br.com.brmalls.xpto_client.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.brmalls.xpto_client.Utils;
import br.com.brmalls.xpto_client.dtos.CompanyDTO;
import br.com.brmalls.xpto_client.models.CompanyModel;
import br.com.brmalls.xpto_client.services.CompanyService;

@Controller
@RequestMapping( value = "/company" )
public class CompanyController {
	
	@PostMapping( value = "/" )
	@ResponseBody
	public CompanyModel create( @RequestBody final CompanyModel company ) {
		
		this.service.create( company );
		
		return company;
	}
	
	@GetMapping( value = "/cnpj/{cnpj}" )
	@ResponseBody
	public Map<String, String> get( @PathVariable( value = "cnpj" ) String cnpj ) throws IOException {
		
		final Map<String, String> result = new HashMap<String, String>();
		final Boolean cnpjHasMask = Utils.checkCNPJWithMask( cnpj );
		
		if( cnpjHasMask ) {
			cnpj = Utils.clearCNPJMask( cnpj );
		}
		
		final CompanyDTO dto = this.service.getCompany( cnpj );
		final Boolean cnpjExist = dto == null;
		
		if( cnpjExist ) {
			CompanyModel company = this.service.companyByReceitaWS( cnpj );
			
			this.service.create( company );
			
			result.put( "fantasyName", company.fantasia );
			result.put( "socialName", company.nome );
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
