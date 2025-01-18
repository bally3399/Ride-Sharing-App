package com.fortunae.rideService.service;

import com.fortunae.rideService.dtos.requests.CancelRideRequest;
import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.RideResponse;
import com.fortunae.rideService.model.Ride;

import java.util.List;

public interface RideService {
    RideResponse bookRide(RideBookingRequest rideBookingRequest);

    RideResponse cancelRide(String rideId);

    RideResponse acceptRide(String rideId);

//    List<Ride> viewAllRides(String userId);

    List<Ride> viewAllRides(String riderId);

    RideResponse completeRide(String rideId);
}
