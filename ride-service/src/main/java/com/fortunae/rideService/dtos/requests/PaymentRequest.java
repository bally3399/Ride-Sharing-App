package com.fortunae.rideService.dtos.requests;

import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequest {
    private String rideId;
    private String riderId;
    private String driverId;
    private BigDecimal amount;

}
