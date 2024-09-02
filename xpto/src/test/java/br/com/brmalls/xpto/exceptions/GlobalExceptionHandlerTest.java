package br.com.brmalls.xpto.exceptions;

import br.com.brmalls.xpto.dtos.ErrorResponseDTO;
import br.com.brmalls.xpto.utils.CONSTANTS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.String.format;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @Mock
    private WebRequest webRequest;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks( this );
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    private void mockWebRequestDescription() {
        when( webRequest.getDescription( false ) ).thenReturn( "uri=/test" );
    }

    @Test
    void isProcessIllegalArgumentException() {
        mockWebRequestDescription();
        final String invalidCNPJ = "CNPJ_PLACEHOLDER";

        final IllegalArgumentException exception = new IllegalArgumentException( format( CONSTANTS.INVALID_CNPJ, invalidCNPJ ) );
        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processIllegalArgumentException( exception, webRequest );

        assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode() );
        assertEquals( format( CONSTANTS.INVALID_CNPJ, invalidCNPJ ), Objects.requireNonNull( response.getBody() ).getMessage() );
        assertEquals( "Bad Request", response.getBody().getError() );
        assertEquals( "/test", response.getBody().getPath() );
    }

    @Test
    void isProcessIllegalStateException() {
        mockWebRequestDescription();

        final IllegalStateException exception = new IllegalStateException( CONSTANTS.RESPONSE_NON_OK );
        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processIllegalStateException( exception, webRequest );

        assertEquals( HttpStatus.CONFLICT, response.getStatusCode() );
        assertEquals( CONSTANTS.RESPONSE_NON_OK, Objects.requireNonNull( response.getBody() ).getMessage() );
        assertEquals( "Conflict", response.getBody().getError() );
        assertEquals( "/test", response.getBody().getPath() );
    }

    @Test
    void isProcessNoSuchElementException() {
        mockWebRequestDescription();

        final NoSuchElementException exception = new NoSuchElementException( CONSTANTS.RESPONSE_NO );
        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processNoSuchElementException( exception, webRequest );

        assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode() );
        assertEquals( CONSTANTS.RESPONSE_NO, Objects.requireNonNull( response.getBody() ).getMessage() );
        assertEquals( "Not Found", response.getBody().getError() );
        assertEquals( "/test", response.getBody().getPath() );
    }

    @Test
    void isProcessRestClientException() {
        mockWebRequestDescription();

        final RestClientException exception = new RestClientException( CONSTANTS.ERROR_QUERYING );
        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.processRestClientException( exception, webRequest );

        assertEquals( HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode() );
        assertEquals( CONSTANTS.ERROR_QUERYING, Objects.requireNonNull( response.getBody() ).getMessage() );
        assertEquals( "Service Unavailable", response.getBody().getError() );
        assertEquals( "/test", response.getBody().getPath() );
    }

    @Test
    void isProcessAllExceptions() {
        mockWebRequestDescription();

        final Exception exception = new Exception( CONSTANTS.ERROR_EXCEPTION );
        final ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler.handleAllExceptions( exception, webRequest );

        assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
        assertEquals( CONSTANTS.ERROR_EXCEPTION, Objects.requireNonNull( response.getBody() ).getMessage() );
        assertEquals( "Internal Server Error", response.getBody().getError() );
        assertEquals( "/test", response.getBody().getPath() );
    }

}
