package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse registerUser(RegisterRiderRequest request);
    void deleteUser(String email);
    GetUserResponse getUser(String email);

    void deleteAll();

    RegisterDriverResponse registerDriver(RegisterDriverRequest driverRequest);
//    LoginResponse loginUser(LoginRequest loginRequest);
//    UpdateUserResponse updateUser(UpdateUserRequest request);

}
