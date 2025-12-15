package com.fastag.backend_services.GlobalExceptionHandler;

public class BadCredentialsCustomException extends RuntimeException {
    public BadCredentialsCustomException(String message) {
        super(message);
    }
}