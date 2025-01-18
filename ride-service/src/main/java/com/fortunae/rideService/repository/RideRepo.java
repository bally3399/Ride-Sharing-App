package com.fortunae.rideService.repository;

import com.fortunae.rideService.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepo extends JpaRepository<Ride, String> {
//    List<Ride> findAll(String riderId);
}
