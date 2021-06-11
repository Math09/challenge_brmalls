package br.com.brmalls.xpto_client.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public CompanyModel companyByReceitaWS( final String cnpj ) throws IOException {
		
		final String receitaWS = String.format( "https://www.receitaws.com.br/v1/cnpj/%s", cnpj );
		
		final URL url = new URL( receitaWS );
		final HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
		
		connection.setRequestMethod( "GET" );
		connection.setRequestProperty( "Content-Type", "application/json" );
		
		connection.setConnectTimeout( 5000 );
		connection.setReadTimeout( 5000 );
		
		final BufferedReader buffer = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
		
		String line = new String();
		String result = new String();
		
		while( ( line = buffer.readLine() ) != null ) {
			result += line;
		}
		
		connection.disconnect();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
		
		CompanyModel company = objectMapper.readValue( result, CompanyModel.class );
		
		return company;	
	}
	
	@Autowired
	private CompanyDao dao;
	
}
