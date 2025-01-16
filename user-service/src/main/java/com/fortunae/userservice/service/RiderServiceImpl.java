package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.dtos.response.RideResponse;
import com.fortunae.userservice.exceptions.InvalidDetailsException;
import com.fortunae.userservice.exceptions.NotFoundException;
import com.fortunae.userservice.exceptions.UserExistsException;
import com.fortunae.userservice.model.Rider;
import com.fortunae.userservice.repository.RiderRepository;
import com.fortunae.userservice.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fortunae.userservice.utils.ValidationUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;
    private final RiderRepository riderRepository;

    @Value("${ride-service-url}")
    String rideServiceUrl;

    @Autowired
    private WebClient webClient;


    @Override
    public RegisterUserResponse registerUser(RegisterRiderRequest request) {
        validateFields(request.getPhoneNumber(), request.getEmail(), request.getPassword());
        doesUserExists(request.getEmail());
        Rider user = modelMapper.map(request, Rider.class);
        user = riderRepository.save(user);
        RegisterUserResponse response = modelMapper.map(user, RegisterUserResponse.class);
        response.setMessage("Rider registered successfully");
        return response;
    }

    private void validateFields(String phoneNumber, String email, String password) {
        if (!isValidPhoneNumber(phoneNumber))
            throw new InvalidDetailsException("The phone number you entered is not correct");
        if (!isValidEmail(email)) throw new InvalidDetailsException("The email you entered is not correct");
        if (!isValidPassword(password))
            throw new InvalidDetailsException("Password must be between 8 and 16 characters long, including at least one uppercase letter, one lowercase letter, one number, and one special character (e.g., @, #, $, %, ^).");
    }

    private void doesUserExists(String email){
        Rider user = riderRepository.findByEmail(email).orElse(null);
        if (user != null)throw new UserExistsException(String.format("User with email: %s already exits", email));
    }


    @Override
    public void deleteUser(String email) {
        Rider savedUser = riderRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", email)));
        riderRepository.delete(savedUser);
    }

    @Override
    public GetUserResponse getUser(String email) {
        Rider user = riderRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", email)));
        return modelMapper.map(user, GetUserResponse.class);
    }

    @Override
    public Rider getRiderById(String riderId){
        return riderRepository.findById(riderId)
                .orElseThrow(()->
                        new NotFoundException(String.format("Rider with id: %s not found", riderId)));
    }
    @Override
    public void deleteAll() {
        riderRepository.deleteAll();
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return checkLoginDetail(email, password);
    }

    private LoginResponse checkLoginDetail(String email, String password) {
        Optional<Rider> optionalUser = riderRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            Rider user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return loginResponseMapper(user);
            } else {
                throw new InvalidDetailsException("Invalid username or password");
            }
        } else {
            throw new InvalidDetailsException("Invalid username or password");
        }
    }

    private LoginResponse loginResponseMapper(Rider rider) {
        LoginResponse loginResponse = new LoginResponse();
        String accessToken = JwtUtils.generateAccessToken(rider.getId());
        BeanUtils.copyProperties(rider, loginResponse);
        loginResponse.setJwtToken(accessToken);
        loginResponse.setMessage("Login Successful");
        loginResponse.setRole(rider.getRole().toString());
        return loginResponse;
    }


//    @Override
//    public List<RideResponse> getRidesForUser(String userId) {
//        String url = String.format(rideServiceUrl + "", userId);
//
//        RideResponse[] rides = webClient
//                .get()
//                .uri(url)
//                .retrieve()
//                .onStatus(
//                        HttpStatusCode::is4xxClientError,
//                        response -> Mono.error(new RuntimeException("No rides found for user: " + userId))
//                )
//                .onStatus(
//                        HttpStatusCode::is5xxServerError,
//                        response -> Mono.error(new RuntimeException("Ride-service is unavailable"))
//                )
//                .bodyToMono(RideResponse[].class)
//                .block();
//
//        return Arrays.asList(rides);
//    }


}
