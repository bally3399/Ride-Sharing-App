package com.fortunae.userservice.service;


import com.fortunae.userservice.model.Token;

public interface TokenService {

    String  createToken(String email);

    Token findByUserEmail(String email);

    void deleteToken(String id);

}
