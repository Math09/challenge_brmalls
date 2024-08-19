package br.com.brmalls.xpto.exceptions;

import br.com.brmalls.xpto.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDTO> errorResponse( HttpStatus status, String error, String message, WebRequest request ) {
        final String path = request.getDescription( false ).replace( "uri=", "" );
        final ErrorResponseDTO errorResponse = new ErrorResponseDTO( status.value(), error, message, path );

        return new ResponseEntity<>( errorResponse, status );
    }

    @ExceptionHandler( IllegalArgumentException.class )
    public ResponseEntity<ErrorResponseDTO> processIllegalArgumentException( IllegalArgumentException exception, WebRequest request ) {
        return errorResponse( HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage(), request );
    }

    @ExceptionHandler( IllegalStateException.class )
    public ResponseEntity<ErrorResponseDTO> processIllegalStateException( IllegalStateException exception, WebRequest request ) {
        return errorResponse( HttpStatus.CONFLICT, "Conflict", exception.getMessage(), request );
    }

    @ExceptionHandler( NoSuchElementException.class )
    public ResponseEntity<ErrorResponseDTO> processNoSuchElementException( NoSuchElementException exception, WebRequest request ) {
        return errorResponse( HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request );
    }

    @ExceptionHandler( RestClientException.class )
    public ResponseEntity<ErrorResponseDTO> processRestClientException( RestClientException exception, WebRequest request ) {
        return errorResponse( HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable", exception.getMessage(), request );
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions( Exception exception, WebRequest request ) {
        return errorResponse( HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", exception.getMessage(), request );
    }

}
