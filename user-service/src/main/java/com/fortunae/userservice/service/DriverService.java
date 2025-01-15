package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.model.Driver;

public interface DriverService {
    RegisterDriverResponse registerDriver(RegisterDriverRequest driverRequest);
    void deleteUser(String email);
    GetUserResponse getUser(String email);

    void deleteAll();

    LoginResponse login(LoginRequest loginRequest);

//    Driver getAvailableDriver(String email);

    Driver getAvailableDriver(String id);
}
