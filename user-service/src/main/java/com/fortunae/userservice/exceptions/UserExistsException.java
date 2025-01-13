package com.fortunae.userservice.exceptions;

public class UserExistsException extends RideShareException {
    public UserExistsException(String format) {
        super(format);
    }
}
