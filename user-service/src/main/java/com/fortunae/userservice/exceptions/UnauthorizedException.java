package com.fortunae.userservice.exceptions;

public class UnauthorizedException extends RideShareException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
