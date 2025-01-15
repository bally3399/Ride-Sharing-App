package com.fortunae.rideService.repository;

import com.fortunae.rideService.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepo extends JpaRepository<Ride, String> {
}
