package com.fortunae.rideService.service;

import com.fortunae.rideService.dtos.requests.CancelRideRequest;
import com.fortunae.rideService.dtos.requests.RideBookingRequest;
import com.fortunae.rideService.dtos.response.CancelRideResponse;
import com.fortunae.rideService.dtos.response.RideBookingResponse;
import com.fortunae.rideService.dtos.response.UserResponse;
import com.fortunae.rideService.model.Ride;
import com.fortunae.rideService.repository.RideRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.fortunae.rideService.model.Status.CANCELED;

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
    public RideBookingResponse bookRide(RideBookingRequest rideBookingRequest) {
        getRiderById(rideBookingRequest.getRiderId());
        getAvailableDriver(rideBookingRequest.getDriverId());
        Ride ride = Ride.builder()
                .driverId(rideBookingRequest.getDriverId())
                .riderId(rideBookingRequest.getRiderId())
                .dropLocation(rideBookingRequest.getDropLocation())
                .pickupLocation(rideBookingRequest.getPickupLocation())
                .status(rideBookingRequest.getStatus())
                .build();
        ride = rideRepo.save(ride);
        RideBookingResponse rideBookingResponse = modelMapper.map(ride, RideBookingResponse.class);
        rideBookingResponse.setMessage("Ride Booked Successful");
        return rideBookingResponse;

    }

    @Override
    public CancelRideResponse cancelRide(CancelRideRequest cancelRideRequest) {
        Ride ride = rideRepo.findById(cancelRideRequest.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getRiderId().equals(cancelRideRequest.getRiderId()) && !ride.getDriverId().equals(cancelRideRequest.getRiderId())) {
            throw new RuntimeException("User is not authorized to cancel this ride");
        }
        ride.setStatus(CANCELED);
        rideRepo.save(ride);
        CancelRideResponse cancelRideResponse = modelMapper.map(ride, CancelRideResponse.class);
        cancelRideResponse.setMessage("Ride Cancelled");
        return cancelRideResponse;
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

}
