package com.fortunae.userservice.repository;

import com.fortunae.userservice.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, String> {
    Optional<Rider> findByEmail(String email);
}
