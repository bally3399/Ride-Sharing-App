package com.fortunae.rideService.service;

import com.fortunae.rideService.dtos.requests.CancelRideRequest;
import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.RideResponse;
import com.fortunae.rideService.exception.RideAlreadyAcceptedException;
import com.fortunae.rideService.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class RideServiceImplTest {
    @Autowired
    private RideService rideService;
    private RideResponse rideResponse;

    @BeforeEach
    public void setUp(){
        RideBookingRequest rideBookingRequest = new RideBookingRequest();
        rideBookingRequest.setRiderId("9915b560-0031-4252-b7f9-35acf7f5788a");
        rideBookingRequest.setDriverId("14b4efe2-293e-497c-8910-21c971c57a3c");
        rideBookingRequest.setPickupLocation("Mary land");
        rideBookingRequest.setStatus(Status.PENDING);
        rideBookingRequest.setPrice(new BigDecimal(5000));
        rideBookingRequest.setDropLocation("Bariga");
        rideResponse = rideService.bookRide(rideBookingRequest);
    }
    @Test
    public void RiderCanBookRide(){

        assertThat(rideResponse.getPickupLocation()).isEqualTo("Mary land");
    }

    @Test
    public void RiderCanUnBookRide(){
        String id = rideResponse.getRideId();
        RideResponse response = rideService.cancelRide(id);
        assertThat(response.getRideId()).isEqualTo(id);

    }
    @Test
    public void acceptRideTest(){
        String id = rideResponse.getRideId();
        RideResponse response = rideService.acceptRide(id);
        assertThat(response.getRideId()).isEqualTo(id);
    }
    @Test
    public void acceptNonExistentRideTest() {
        String invalidId = "non-existent-id";

        assertThatThrownBy(() -> rideService.acceptRide(invalidId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ride not found");
    }

    @Test
    public void acceptAlreadyAcceptedRideTest() {
        String id = rideResponse.getRideId();
        rideService.acceptRide(id);

        assertThatThrownBy(() -> rideService.acceptRide(id))
                .isInstanceOf(RideAlreadyAcceptedException.class)
                .hasMessage(String.format("Ride with ID %s is already accepted", id));
    }
}