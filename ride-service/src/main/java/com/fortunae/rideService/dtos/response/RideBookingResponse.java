package com.fortunae.rideService.dtos.response;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RideBookingResponse {
    private String rideId;
    private String riderId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private String message;
    private BigDecimal price;

}

