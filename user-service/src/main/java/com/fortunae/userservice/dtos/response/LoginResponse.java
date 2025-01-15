package com.fortunae.userservice.dtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String message;
    private String jwtToken;
    private String role;
    private String firstName;
    private String lastName;


}
