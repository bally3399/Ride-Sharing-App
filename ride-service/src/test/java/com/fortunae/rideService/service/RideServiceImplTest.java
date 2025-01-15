package com.fortunae.rideService.service;

import com.fortunae.rideService.dtos.requests.CancelRideRequest;
import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.CancelRideResponse;
import com.fortunae.rideService.dtos.response.RideBookingResponse;
import com.fortunae.rideService.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RideServiceImplTest {
    @Autowired
    private RideService rideService;
    private RideBookingResponse rideBookingResponse;

    @BeforeEach
    public void setUp(){
        RideBookingRequest rideBookingRequest = new RideBookingRequest();
        rideBookingRequest.setRiderId("4bd0fb8a-c563-4cad-a000-fe3a568542e7");
        rideBookingRequest.setDriverId("2bea320d-b28b-4cbf-a193-1ecabbd92f5f");
        rideBookingRequest.setPickupLocation("Mary land");
        rideBookingRequest.setStatus(Status.PENDING);
        rideBookingRequest.setPrice(new BigDecimal(5000));
        rideBookingRequest.setDropLocation("Lagos");
        rideBookingResponse = rideService.bookRide(rideBookingRequest);
    }
    @Test
    public void RiderCanBookRide(){

        assertThat(rideBookingResponse.getMessage()).isEqualTo("Ride Booked Successful");
    }

    @Test
    public void RiderCanUnBookRide(){

        CancelRideRequest cancelRideRequest = new CancelRideRequest();
        cancelRideRequest.setRideId(rideBookingResponse.getRideId());
        cancelRideRequest.setRiderId("4bd0fb8a-c563-4cad-a000-fe3a568542e7");
        CancelRideResponse response = rideService.cancelRide(cancelRideRequest);
        assertThat(response.getMessage()).isEqualTo("Ride Cancelled");


    }
}