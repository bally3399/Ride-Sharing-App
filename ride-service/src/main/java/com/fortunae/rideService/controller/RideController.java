package com.fortunae.rideService.controller;

import com.fortunae.rideService.dtos.requests.CancelRideRequest;
import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.RideResponse;
import com.fortunae.rideService.model.Ride;
import com.fortunae.rideService.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@Slf4j
@RequiredArgsConstructor
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping("/book-ride")
    public ResponseEntity<RideResponse> rideBooking(@Valid @RequestBody RideBookingRequest request) {
        RideResponse response = rideService.bookRide(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{rideId}")
    public ResponseEntity<RideResponse> cancelRide(@PathVariable String rideId) {
        RideResponse response = rideService.cancelRide(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/{rideId}/ride")
    public ResponseEntity<RideResponse> acceptRide(@PathVariable String rideId) {
        RideResponse response = rideService.acceptRide(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{rideId}/rides")
    public ResponseEntity<List<Ride>> viewRides(@PathVariable String rideId) {
        List<Ride> response = rideService.viewAllRides(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
