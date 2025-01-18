package com.fortunae.paymentService.controller;

import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;
import com.fortunae.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initialise")
    public ResponseEntity<InitialisePaymentResponse> initialisePayment(
            @RequestParam String email,
            @RequestParam BigDecimal amount) {
        InitialisePaymentResponse response = paymentService.initialisePayment(email, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(@RequestParam String reference) {
        VerifyPaymentResponse response = paymentService.verifyPayment(reference);
        return ResponseEntity.ok(response);
    }
}
