package br.com.brmalls.xpto.controllers;

import br.com.brmalls.xpto.dtos.ErrorResponseDTO;
import br.com.brmalls.xpto.exceptions.GlobalExceptionHandler;
import br.com.brmalls.xpto.models.CompanyModel;
import br.com.brmalls.xpto.services.CompanyService;
import br.com.brmalls.xpto.utils.CONSTANTS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private CompanyController companyController;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks( this );
    }

    private void mockWebRequestDescription( String cnpj ) {
        when( webRequest.getDescription( false ) ).thenReturn( "uri=/company/" + cnpj );
    }

    @ParameterizedTest
    @CsvSource(  { "CNPJ_PLACEHOLDER, SOCIAL_NAME_PLACEHOLDER" } )
    void isGetCompanyByCNPJ( String cnpj, String socialName ) {
        final CompanyModel company = new CompanyModel();
        company.setCnpj( cnpj );
        company.setSocialName( socialName );

        when( companyService.getCompanyByCnpj( cnpj ) ).thenReturn( company );

        ResponseEntity<CompanyModel> response = companyController.getCompanyByCNPJ( cnpj );

        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( company, response.getBody() );
    }

    @ParameterizedTest
    @ValueSource( strings = { "invalid_cnpj", "" } )
    void isReturnIllegalArgumentException( String invalidCNPJ ) {
        final String expectedMessage = String.format( CONSTANTS.INVALID_CNPJ, invalidCNPJ );
        when( companyService.getCompanyByCnpj( invalidCNPJ ) ).thenThrow( new IllegalArgumentException( expectedMessage ) );

        mockWebRequestDescription( invalidCNPJ );

        final IllegalArgumentException exception = assertThrows( IllegalArgumentException.class, () -> {
            companyController.getCompanyByCNPJ( invalidCNPJ );
        } );

        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processIllegalArgumentException(
                exception,
                webRequest
        );

        assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode() );
        assertEquals( expectedMessage, Objects.requireNonNull( response.getBody() ).getMessage() );
    }

    @ParameterizedTest
    @ValueSource( strings = { "INVALID_CNPJ_PLACEHOLDER" } )
    void isReturnIllegalStateException( String cnpj ) {
        final String expectedMessage = String.format( CONSTANTS.RESPONSE_NON_OK, cnpj );
        when( companyService.getCompanyByCnpj( cnpj ) ).thenThrow( new IllegalStateException( expectedMessage ) );

        mockWebRequestDescription( cnpj );

        final IllegalStateException exception = assertThrows( IllegalStateException.class, () -> {
            companyController.getCompanyByCNPJ( cnpj );
        } );

        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processIllegalStateException(
                exception,
                webRequest
        );

        assertEquals( HttpStatus.CONFLICT, response.getStatusCode() );
        assertEquals( expectedMessage, Objects.requireNonNull( response.getBody() ).getMessage() );
    }

    @ParameterizedTest
    @ValueSource( strings = { "INVALID_CNPJ_PLACEHOLDER", "INVALID_CNPJ_PLACEHOLDER" } )
    void isReturnNoSuchElementException( String cnpj ) {
        final String expectedMessage = String.format( CONSTANTS.RESPONSE_NO, cnpj );
        when( companyService.getCompanyByCnpj( cnpj ) ).thenThrow( new NoSuchElementException( expectedMessage ) );

        mockWebRequestDescription( cnpj );

        final NoSuchElementException exception = assertThrows( NoSuchElementException.class, () -> {
            companyController.getCompanyByCNPJ( cnpj );
        } );

        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processNoSuchElementException(
                exception,
                webRequest
        );

        assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode() );
        assertEquals( expectedMessage, Objects.requireNonNull( response.getBody() ).getMessage() );
    }

    @ParameterizedTest
    @ValueSource( strings = { "INVALID_CNPJ_PLACEHOLDER" } )
    void isReturnRestClientException( String cnpj ) {
        final String expectedMessage = String.format( CONSTANTS.ERROR_QUERYING, cnpj );
        when( companyService.getCompanyByCnpj( cnpj ) ).thenThrow( new RestClientException( expectedMessage ) );

        mockWebRequestDescription( cnpj );

        final RestClientException exception = assertThrows( RestClientException.class, () -> {
            companyController.getCompanyByCNPJ( cnpj );
        } );

        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processRestClientException(
                exception,
                webRequest
        );

        assertEquals( HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode() );
        assertEquals( expectedMessage, Objects.requireNonNull( response.getBody() ).getMessage() );
    }

    @ParameterizedTest
    @ValueSource( strings = { "INVALID_CNPJ_PLACEHOLDER" } )
    void isReturnGenericException( String cnpj ) {
        final String expectedMessage = String.format( CONSTANTS.ERROR_QUERYING, cnpj );
        when( companyService.getCompanyByCnpj( cnpj ) ).thenThrow( new RuntimeException( expectedMessage ) );

        mockWebRequestDescription( cnpj );

        final Exception exception = assertThrows( RuntimeException.class, () -> {
            companyController.getCompanyByCNPJ( cnpj );
        } );

        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleAllExceptions(
                (RuntimeException) exception,
                webRequest
        );

        assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
        assertEquals( expectedMessage, Objects.requireNonNull( response.getBody() ).getMessage() );
    }

}
