package com.fortunae.paymentService.exception;

public class ExternalApiException extends RuntimeException{
    public ExternalApiException(String message) {
        super(message);
    }
}
