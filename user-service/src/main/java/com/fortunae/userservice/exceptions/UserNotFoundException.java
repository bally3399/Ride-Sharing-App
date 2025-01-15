package com.fortunae.userservice.exceptions;

public class UserNotFoundException extends RideShareException {
    public UserNotFoundException(String provideValidMail) {
        super(provideValidMail);
    }
}
