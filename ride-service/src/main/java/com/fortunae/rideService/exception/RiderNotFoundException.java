package com.fortunae.rideService.exception;

public class RiderNotFoundException extends RideExceptions{
    public RiderNotFoundException(String riderNotFound) {
        super(riderNotFound);
    }
}
