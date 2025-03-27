package com.example.demo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GarageException extends RuntimeException {

    HttpStatus statusCode;
    private ClientErrorCode clientErrorCode;


    public GarageException(String message) {
        super(message);
    }

    public GarageException(String message, Throwable t) {
        super(message, t);
    }

    public GarageException(String message, Throwable t, HttpStatus statusCode) {
        super(message, t);
        this.statusCode = statusCode;
    }

    public GarageException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public GarageException(String message, HttpStatus statusCode, ClientErrorCode clientErrorCode) {
        super(message);
        this.statusCode = statusCode;
        this.clientErrorCode = clientErrorCode;
    }


}
