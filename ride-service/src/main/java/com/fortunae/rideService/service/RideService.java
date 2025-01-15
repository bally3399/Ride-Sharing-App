package com.fortunae.rideService.service;

import com.fortunae.rideService.dtos.requests.CancelRideRequest;
import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.CancelRideResponse;
import com.fortunae.rideService.dtos.response.RideBookingResponse;

public interface RideService {
    RideBookingResponse bookRide(RideBookingRequest rideBookingRequest);

    CancelRideResponse cancelRide(CancelRideRequest cancelRideRequest);
}
