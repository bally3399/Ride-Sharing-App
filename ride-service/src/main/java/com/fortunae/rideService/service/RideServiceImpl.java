package com.fortunae.rideService.service;

import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.RideResponse;
import com.fortunae.rideService.dtos.response.UserResponse;
import com.fortunae.rideService.exception.RideAlreadyAcceptedException;
import com.fortunae.rideService.model.Ride;
import com.fortunae.rideService.model.Status;
import com.fortunae.rideService.repository.RideRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.fortunae.rideService.model.Status.*;

@Service
public class RideServiceImpl implements RideService {

    @Autowired
    private ModelMapper modelMapper;
    @Value("${user-service-url}")
    String serviceName;

    @Autowired
    private WebClient webClient;

    @Autowired
    private RideRepo rideRepo;

    @Override
    public RideResponse bookRide(RideBookingRequest rideBookingRequest) {
        getRiderById(rideBookingRequest.getRiderId());
        getAvailableDriver(rideBookingRequest.getDriverId());
        Ride ride = Ride.builder()
                .driverId(rideBookingRequest.getDriverId())
                .riderId(rideBookingRequest.getRiderId())
                .dropLocation(rideBookingRequest.getDropLocation())
                .pickupLocation(rideBookingRequest.getPickupLocation())
                .status(rideBookingRequest.getStatus())
                .build();
        if (ride.getStatus() == PENDING) {
            throw new RideAlreadyAcceptedException(String.format("Ride with ID %s is already accepted", ride.getRideId()));
        }
        ride.setStatus(Status.PENDING);
        ride = rideRepo.save(ride);
        return modelMapper.map(ride, RideResponse.class);

    }

    @Override
    public RideResponse cancelRide(String rideId) {
        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() == CANCELED) {
            throw new RideAlreadyAcceptedException(String.format("Ride with ID %s is already accepted", rideId));
        }

        ride.setStatus(CANCELED);
        rideRepo.save(ride);
        return modelMapper.map(ride, RideResponse.class);
    }

    @Override
    public RideResponse acceptRide(String rideId) {
        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        if (ride.getStatus() == Status.ACCEPTED) {
            throw new RideAlreadyAcceptedException(String.format("Ride with ID %s is already accepted", rideId));
        }

        ride.setStatus(ACCEPTED);
        rideRepo.save(ride);
        return modelMapper.map(ride, RideResponse.class);
    }

    private UserResponse getRiderById(String rider) {
        String url = serviceName + "/api/v1/users/{id}";
        UserResponse res = webClient.get()
                .uri(url, rider)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException(String.format("rider not found", rider)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Server error"))
                )
                .bodyToMono(UserResponse.class)
                .block();
        System.out.println(res);
        return res;
    }

    private void getAvailableDriver(String id) {
        String url = serviceName + "/api/v1/drivers/{id}/user";
        webClient.get()
                .uri(url, id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException(String.format("driver not found", id)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Server error"))
                )
                .toBodilessEntity()
                .block();
    }


    @Override
    public List<Ride> viewAllRides(String userId) {
        getRiderById(userId);
        return rideRepo.findAll();
    }

}
