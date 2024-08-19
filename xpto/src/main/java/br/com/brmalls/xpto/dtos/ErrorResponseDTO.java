package br.com.brmalls.xpto.dtos;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private int status;

    private String error;

    private String message;

    private String path;

    public ErrorResponseDTO( int status, String error, String message, String path ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ErrorResponseDTO{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

}
