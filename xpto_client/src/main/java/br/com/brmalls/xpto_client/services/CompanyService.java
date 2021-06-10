package br.com.brmalls.xpto_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brmalls.xpto_client.Utils;
import br.com.brmalls.xpto_client.daos.CompanyDao;
import br.com.brmalls.xpto_client.dtos.CompanyDTO;
import br.com.brmalls.xpto_client.models.CompanyModel;

@Service
public class CompanyService extends AbstractService<CompanyModel> {
	
	
	public CompanyModel create( final CompanyModel company ) {
		
		final Boolean cnpjHasMask = Utils.checkCNPJWithMask( company.cnpj );
		
		if( cnpjHasMask ) {
			company.cnpj = Utils.clearCNPJMask( company.cnpj );
		}
		
		this.dao.save( company );
		
		return company;
	}
	
	public CompanyDTO getCompany( final String cnpj ) {
		
		return this.dao.getCompanyByCnpj( cnpj );
	}
	
	@Autowired
	private CompanyDao dao;
	
}
