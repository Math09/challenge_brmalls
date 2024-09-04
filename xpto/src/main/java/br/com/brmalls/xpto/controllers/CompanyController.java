package br.com.brmalls.xpto.controllers;

import br.com.brmalls.xpto.models.CompanyModel;
import br.com.brmalls.xpto.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping( "/company" )
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController( CompanyService companyService ) {
        this.companyService = companyService;
    }

    @GetMapping( "/{cnpj}" )
    @ResponseBody
    public ResponseEntity<CompanyModel> getCompanyByCNPJ( @PathVariable String cnpj ) {
        return ResponseEntity.ok( companyService.getCompanyByCnpj( cnpj ) );
    }

}
