package br.com.brmalls.xpto_client;

import org.springframework.http.ResponseEntity;

public class Utils {
	
	private static final String MASK = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}";
	
	public static Boolean checkCNPJWithMask( String cnpj ) {
		
		final Boolean hasMask = cnpj.matches( MASK );
		
		return hasMask;
	}
	
	public static String clearCNPJMask( String cnpj ) {
		
		final String cleanCnpj = cnpj.replaceAll( "[\\.\\/\\-\\^]", "" );
		
		return cleanCnpj;
	}
	
}
