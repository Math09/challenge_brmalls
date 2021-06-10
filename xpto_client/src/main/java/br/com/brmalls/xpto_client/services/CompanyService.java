package br.com.brmalls.xpto_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brmalls.xpto_client.daos.CompanyDao;
import br.com.brmalls.xpto_client.models.CompanyModel;

@Service
public class CompanyService extends AbstractService<CompanyModel> {
	
	private static final String MASK = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}";
	
	public CompanyModel create( final CompanyModel company ) {
		
		final Boolean cnpjHasMask = this.checkCNPJWithMask( company.cnpj );
		
		if( cnpjHasMask ) {
			company.cnpj = this.clearCNPJMask( company.cnpj );
		}
		
		this.dao.save( company );
		
		return company;
	}
	
	public Boolean checkCNPJWithMask( String cnpj ) {
		
		final Boolean hasMask = cnpj.matches( MASK );
		
		return hasMask;
	}
	
	public String clearCNPJMask( String cnpj ) {
		
		final String cleanCnpj = cnpj.replaceAll( "[\\.\\/\\-\\^]", "" );
		
		return cleanCnpj;
	}
	
	@Autowired
	private CompanyDao dao;
	
}
