package com.fortunae.rideService.dtos.response;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private String id;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String vehicleModel;
    private String licensePlate;
    private String vehicleColor;
    private boolean available;
    private String message;
}
