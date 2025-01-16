package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.dtos.response.RideResponse;
import com.fortunae.userservice.model.Rider;

import java.util.List;

public interface RiderService {
    RegisterUserResponse registerUser(RegisterRiderRequest request);

    void deleteUser(String email);
    GetUserResponse getUser(String email);

    Rider getRiderById(String riderId);

    void deleteAll();

    LoginResponse login(LoginRequest loginRequest);

//    List<RideResponse> getRidesForUser(String userId);


}
