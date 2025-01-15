package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.model.Rider;

public interface RiderService {
    RegisterUserResponse registerUser(RegisterRiderRequest request);

    void deleteUser(String email);
    GetUserResponse getUser(String email);

    Rider getRiderById(String riderId);

    void deleteAll();

    LoginResponse login(LoginRequest loginRequest);

//    LoginResponse loginUser(LoginRequest loginRequest);
//    UpdateUserResponse updateUser(UpdateUserRequest request);

}
