package br.com.brmalls.xpto.services.impls;

import br.com.brmalls.xpto.daos.CompanyDAO;
import br.com.brmalls.xpto.dtos.CompanyDataResponseDTO;
import br.com.brmalls.xpto.models.CompanyModel;
import br.com.brmalls.xpto.services.CompanyService;
import br.com.brmalls.xpto.utils.CNPJUtils;
import br.com.brmalls.xpto.utils.CONSTANTS;
import br.com.brmalls.xpto.utils.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger( CompanyServiceImpl.class );

    private final CompanyDAO companyDao;

    public CompanyServiceImpl( CompanyDAO companyDao ) {
        this.companyDao = companyDao;
    }

    @Override
    public CompanyModel getCompanyByCnpj( String cnpj ) {
        final String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info( CONSTANTS.START_METHOD, methodName, cnpj );

        if( !CNPJUtils.isValidCNPJ( cnpj ) ) {
            logger.error( CONSTANTS.INVALID_CNPJ, cnpj );
            throw new IllegalArgumentException( FormatUtils.formatMessageErrorWithCNPJ( CONSTANTS.INVALID_CNPJ, cnpj ) );
        }

        final String cleanedCNPJ = FormatUtils.formatCNPJ( cnpj );

        return companyDao.findByCnpj( cleanedCNPJ ).orElseGet( () -> {
            CompanyModel company = fetchCompany( cleanedCNPJ );
            companyDao.save( company );

            logger.info( CONSTANTS.COMPANY_SAVED, company );

            return company;
        });
    }

    private CompanyModel fetchCompany( String cnpj ) {
        final String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        logger.info( CONSTANTS.START_METHOD, methodName, cnpj );

        final RestTemplate restTemplate = new RestTemplate();
        final String url = CONSTANTS.URL_TAX_SERVICE.concat( cnpj );

        try {
            logger.info( CONSTANTS.QUERYING_COMPANY, cnpj );

            CompanyDataResponseDTO response = restTemplate.getForObject( url, CompanyDataResponseDTO.class );

            if( response == null ) {
                logger.error( CONSTANTS.RESPONSE_NO, cnpj );
                throw new NoSuchElementException( FormatUtils.formatMessageErrorWithCNPJ( CONSTANTS.RESPONSE_NO, cnpj ) );
            }
            else if( ( "OK" ).equals( response.getStatus() ) ) {
                CompanyModel company = new CompanyModel();

                company.setCnpj( FormatUtils.formatCNPJ( response.getCnpj() ) );
                company.setSocialName( response.getNome() );
                company.setFantasyName( response.getFantasia() );

                logger.info( CONSTANTS.DATA_RECEIVED, company );

                return company;
            }
            else {
                logger.error( CONSTANTS.RESPONSE_NON_OK, response.getStatus() );
                throw new IllegalStateException( FormatUtils.formatMessageErrorWithCNPJ( CONSTANTS.RESPONSE_NON_OK, response.getStatus() ) );
            }
        }
        catch( NoSuchElementException exception ) {
            logger.error( CONSTANTS.RESPONSE_NO, cnpj, exception );
            throw exception;
        }
        catch( IllegalStateException exception ) {
            logger.error( CONSTANTS.RESPONSE_NON_OK, cnpj, exception );
            throw exception;
        }
        catch( RestClientException exception ) {
            logger.error( CONSTANTS.ERROR_QUERYING, cnpj, exception );
            throw new RestClientException( FormatUtils.formatMessageErrorWithCNPJ( CONSTANTS.ERROR_QUERYING, cnpj ) );
        }
        catch( Exception exception ) {
            logger.error( CONSTANTS.ERROR_EXCEPTION, cnpj, exception );
            throw exception;
        }
        finally {
            logger.info( CONSTANTS.END_METHOD, methodName, cnpj );
        }
    }

}
