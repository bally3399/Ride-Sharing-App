package com.fortunae.rideService.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RideResponse {
    private String rideId;
    private String riderId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    @Enumerated(EnumType.STRING)
    private String status;
//    private String message;
    private BigDecimal price;

}

