package com.fortunae.paymentService.service;

import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.InitialisePaymentUseCase;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class PaymentGatewayService implements InitialisePaymentUseCase, VerifyPaymentUseCase {

    private final PaymentGatewayOutputPort paymentGatewayOutputPort;

    @Override
    public InitialisePaymentResponse initialisePayment(String email, BigDecimal amount) {
        return paymentGatewayOutputPort.initialisePayment(email, amount);
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        return paymentGatewayOutputPort.verifyPayment(reference);
    }
}
