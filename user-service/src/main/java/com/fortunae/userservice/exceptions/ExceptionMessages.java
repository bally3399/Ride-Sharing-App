package com.fortunae.userservice.exceptions;

public enum ExceptionMessages {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXIST("User with same username already exists"),

    INVALID_AUTHORIZATION_HEADER_EXCEPTION("Unauthorized user"),

    VERIFICATION_FAILED_EXCEPTION("Verification failed");


    ExceptionMessages(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
