package com.fortunae.rideService.exception;

public class RiderNotFoundException extends RuntimeException{
    public RiderNotFoundException(String riderNotFound) {
        super(riderNotFound);
    }
}
