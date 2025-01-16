package com.fortunae.paymentService.dtos.response;


import java.math.BigDecimal;

public interface InitialisePaymentUseCase {

    InitialisePaymentResponse initialisePayment(String email, BigDecimal amount);
}
