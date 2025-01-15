package com.fortunae.userservice.controller;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@Slf4j
@RequiredArgsConstructor
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/registerDriver")
    public ResponseEntity<RegisterDriverResponse> registerUser(@Valid @RequestBody RegisterDriverRequest driverRequest) {
        RegisterDriverResponse response = driverService.registerDriver(driverRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("email") String email) {
        GetUserResponse response = driverService.getUser(email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("email") String email) {
        driverService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = driverService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<?> getAvailableDriver(@PathVariable("id") String id) {
        driverService.getAvailableDriver(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
