package com.fortunae.rideService.dtos.requests;

import com.fortunae.rideService.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RideBookingRequest {
    @NotEmpty
    @NotBlank
    private String riderId;
    @NotBlank(message = "pick up location is required")
    @NotEmpty(message = "pick up location must not be empty")
    private String pickupLocation;

    @NotBlank(message = "drop location is required")
    @NotEmpty(message = "drop location must not be empty")
    private String dropLocation;

    private Status status;
    private BigDecimal price;

    private String driverId;


}
