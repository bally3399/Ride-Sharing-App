package com.fortunae.userservice.dtos.response;

import com.fortunae.userservice.model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterDriverResponse {
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
    private Role role;
    private String message;
}

