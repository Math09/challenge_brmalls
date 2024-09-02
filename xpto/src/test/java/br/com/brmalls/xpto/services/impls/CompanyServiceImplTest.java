package br.com.brmalls.xpto.services.impls;

import br.com.brmalls.xpto.daos.CompanyDAO;
import br.com.brmalls.xpto.dtos.CompanyDataResponseDTO;
import br.com.brmalls.xpto.models.CompanyModel;

import br.com.brmalls.xpto.utils.FormatUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

public class CompanyServiceImplTest {

    @Mock
    private CompanyDAO companyDao;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks( this );
    }

    @ParameterizedTest
    @CsvSource( {
            "CNPJ_PLACEHOLDER, SOCIAL_NAME_PLACEHOLDER, FANTASY_NAME_PLACEHOLDER",
            "CNPJ_PLACEHOLDER, SOCIAL_NAME_PLACEHOLDER, FANTASY_NAME_PLACEHOLDER",
            "CNPJ_PLACEHOLDER, SOCIAL_NAME_PLACEHOLDER, FANTASY_NAME_PLACEHOLDER"
    } )
    void companyExists( String cnpj, String socialName, String fantasyName ) {
        final String cleanedCNPJ = FormatUtils.formatCNPJ( cnpj );

        final CompanyModel company = new CompanyModel();
        company.setCnpj( cleanedCNPJ );
        company.setSocialName( socialName );
        company.setFantasyName( fantasyName );

        when( companyDao.findByCnpj( anyString() ) ).thenReturn( Optional.of( company ) );

        final CompanyModel result = companyService.getCompanyByCnpj( cnpj );

        assertEquals( company, result );
        verify( companyDao, times( 1 ) ).findByCnpj( cleanedCNPJ );
    }

    @Test
    void isReturnThrowsIllegalArgumentException() {
        final String invalidCNPJ = "CNPJ_PLACEHOLDER";

        assertThrows( IllegalArgumentException.class, () -> companyService.getCompanyByCnpj( invalidCNPJ ) );
        verify( companyDao, never() ).findByCnpj( anyString() );
    }

    @Test
    void isReturnFoundInExternalService() {
        final String cnpj = "CNPJ_PLACEHOLDER";
        final String socialName = "SOCIAL_NAME_PLACEHOLDER";
        final String fantasyName = "FANTASY_NAME_PLACEHOLDER";
        final String cleanedCNPJ = FormatUtils.formatCNPJ( cnpj );

        when( companyDao.findByCnpj( cleanedCNPJ ) ).thenReturn( Optional.empty() );

        final CompanyDataResponseDTO responseDTO = new CompanyDataResponseDTO();
        responseDTO.setCnpj( cnpj );
        responseDTO.setNome( socialName );
        responseDTO.setFantasia( fantasyName );
        responseDTO.setStatus( "OK" );

        when( restTemplate.getForObject( anyString(), eq( CompanyDataResponseDTO.class ) ) ).thenReturn( responseDTO );

        final CompanyModel result = companyService.getCompanyByCnpj( cnpj );

        assertNotNull( result );
        assertEquals( cleanedCNPJ, result.getCnpj() );
        assertEquals( socialName, result.getSocialName() );
        assertEquals( fantasyName, result.getFantasyName() );

        verify( companyDao, times( 1 ) ).save( any( CompanyModel.class ) );
    }

}
