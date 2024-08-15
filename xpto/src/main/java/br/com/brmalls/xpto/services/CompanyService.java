package br.com.brmalls.xpto.services;

import br.com.brmalls.xpto.models.CompanyModel;

public interface CompanyService {

    CompanyModel getCompanyByCnpj( String cnpj );

}
