package com.fortunae.rideService.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelRideRequest {
    private String rideId;
    private String riderId;
}
