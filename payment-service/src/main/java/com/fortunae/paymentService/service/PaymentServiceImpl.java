package com.fortunae.paymentService.service;

import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;
import com.fortunae.paymentService.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${paystack.api.key}")
    private String paystackSecretKey;
    @Value("${PAYSTACK_INITIALIZE_PAY_URL}")
    private String PAYSTACK_INITIALIZE_PAY_URL;
    @Value("${PAYSTACK_VERIFY_URL}")
    private String PAYSTACK_VERIFY_URL;


    private final WebClient webClient;

    @Override
    public InitialisePaymentResponse initialisePayment(String email, BigDecimal amount) {
        return webClient.post()
                .uri(PAYSTACK_INITIALIZE_PAY_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"email\":\"" + email + "\",\n\"amount\":\"" + amount + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new DepositRequestException("Invalid deposit details"))                 )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to initialise payment due to server error"))
                )
                .bodyToMono(InitialisePaymentResponse.class).block();
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        return webClient.get()
                .uri(PAYSTACK_VERIFY_URL + reference)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new InvalidPaymentReferenceException("Invalid payment reference"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to verify payment due to server error"))
                )
                .bodyToMono(VerifyPaymentResponse.class).block();
    }

}
