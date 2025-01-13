package com.fortunae.userservice.dtos.request;

import com.fortunae.userservice.model.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterDriverRequest {
    @NotBlank(message = "Username is required")
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "First name is required")
    @NotEmpty(message = "First name must not be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotEmpty(message = "Last name must not be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @NotEmpty(message = "Phone number must not be empty")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    @NotBlank(message = "Vehicle model is required")
    @NotEmpty(message = "Vehicle model must not be empty")
    private String vehicleModel;

    @NotBlank(message = "License plate is required")
    @NotEmpty(message = "License plate must not be empty")
    private String licensePlate;

    @NotBlank(message = "Vehicle color is required")
    @NotEmpty(message = "Vehicle color must not be empty")
    private String vehicleColor;

    @NotBlank()
    @NotEmpty()
    private boolean available;
}
