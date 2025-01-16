package com.fortunae.paymentService.exception;

public class InvalidPaymentReferenceException extends RuntimeException{
    public InvalidPaymentReferenceException(String message) {
        super(message);
    }
}
