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
        rideBookingRequest.setRiderId("bb304bd4-6612-4eea-a709-b31f627c205d");
        rideBookingRequest.setDriverId("222ad69f-57e7-47ea-8582-fe7dcd4bc869");
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