package com.fortunae.paymentService.service;


import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;

import java.math.BigDecimal;

public interface PaymentService {

    InitialisePaymentResponse initialisePayment(String email, BigDecimal amount);

    VerifyPaymentResponse verifyPayment(String reference);

}
