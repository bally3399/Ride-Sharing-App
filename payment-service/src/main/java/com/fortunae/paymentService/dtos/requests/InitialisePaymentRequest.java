package com.fortunae.paymentService.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class InitialisePaymentRequest {
    private String email;
    private BigDecimal amount;
}
