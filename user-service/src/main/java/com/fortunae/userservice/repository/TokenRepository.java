package com.fortunae.userservice.repository;

import com.fortunae.userservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByOwnerEmail(String lowerCase);
}
