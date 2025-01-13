package com.fortunae.userservice.dtos.response;

import com.fortunae.userservice.model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetUserResponse {
    private String id;
    private String firstName;
    private String username;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
}
