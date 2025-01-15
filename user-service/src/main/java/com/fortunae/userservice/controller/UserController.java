package com.fortunae.userservice.controller;

import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.FindByIdResponse;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.model.Rider;
import com.fortunae.userservice.service.RiderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final RiderService userService;

    @PostMapping("/registerRider")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterRiderRequest request) {
        RegisterUserResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




    @GetMapping("/by-email/{email}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("email") String email) {
        GetUserResponse response = userService.getUser(email);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rider> getId(@PathVariable("id") String id) {
       Rider rider = userService.getRiderById(id);
       return ResponseEntity.ok(rider);

    }

}
