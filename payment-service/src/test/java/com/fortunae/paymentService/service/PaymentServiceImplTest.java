package com.fortunae.paymentService.service;

import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;
import com.fortunae.paymentService.exception.InvalidPaymentReferenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceImplTest {
    @Autowired
    private PaymentService paymentService;


    @Test
    public void initialisePayment() {
        String email = "alexhunt@gmail.com";
        BigDecimal amount = new BigDecimal(5000);
        InitialisePaymentResponse response = paymentService.initialisePayment(email, amount);

        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
        assertThat(response.getData().getReference()).isNotNull();
        assertEquals("Authorization URL created", response.getMessage());
    }

    @Test
    public void verifyPayment() {
        VerifyPaymentResponse response = paymentService.verifyPayment("qjei56x15i");

        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isTrue();
        assertEquals("Verification successful", response.getMessage());
        assertEquals("success", response.getData().getStatus());
    }

    @Test
    public void verifyNonExistentReference_ThrowsExceptionTest() {
        assertThrows(InvalidPaymentReferenceException.class, ()-> paymentService.verifyPayment("non existent reference"));
    }


}