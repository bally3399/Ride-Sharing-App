package com.fortunae.paymentService.service;

import com.fortunae.paymentService.dtos.response.CreateTransferRecipientResponse;
import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.InitiateTransferResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;
import com.fortunae.paymentService.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;


@RequiredArgsConstructor
public class PaystackAdapter implements PaymentGatewayOutputPort {

    @Value("${paystack.api.key}")
    private String paystackSecretKey;
    @Value("${PAYSTACK_INITIALIZE_PAY_URL}")
    private String PAYSTACK_INITIALIZE_PAY_URL;
    @Value("${PAYSTACK_VERIFY_URL}")
    private String PAYSTACK_VERIFY_URL;
    @Value("${PAYSTACK_CREATE_RECIPIENT_URL}")
    private String PAYSTACK_CREATE_RECIPIENT_URL;
    @Value("${PAYSTACK_INITIATE_TRANSFER_URL}")
    private String PAYSTACK_INITIATE_TRANSFER_URL;


    private final WebClient webClient;

    @Override
    public InitialisePaymentResponse initialisePayment(String email, BigDecimal amount) {
        InitialisePaymentResponse response = webClient.post()
                .uri(PAYSTACK_INITIALIZE_PAY_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"email\":\"" + email + "\",\n\"amount\":\"" + amount + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new DepositRequestException("Invalid deposit details")) // change
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to initialise payment due to server error"))
                )
                .bodyToMono(InitialisePaymentResponse.class).block();
        return response;
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) {
        VerifyPaymentResponse response = webClient.get()
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
        return response;
    }

    @Override
    public CreateTransferRecipientResponse createTransferRecipient(String accountNumber, String bankCode) {
        CreateTransferRecipientResponse response = webClient.post()
                .uri(PAYSTACK_CREATE_RECIPIENT_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"account_number\":\"" + accountNumber + "\",\n\"bank_code\":\"" + bankCode + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new CreateTransferRecipientException("Invalid transfer details"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to create transfer recipient due to server error"))
                )
                .bodyToMono(CreateTransferRecipientResponse.class).block();

        return  response;
    }

    @Override
    public InitiateTransferResponse initiateTransfer(String source, BigDecimal amount, String recipient, String reason) {
        InitiateTransferResponse response = webClient.post()
                .uri(PAYSTACK_INITIATE_TRANSFER_URL)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + paystackSecretKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"source\":\"" + source + "\",\n" +
                        "\"amount\":\"" + amount + "\",\n" +
                        "\"reference\":\"" + generateTransferReference() + "\",\n" +
                        "\"recipient\":\"" + recipient + "\",\n" +
                        "\"reason\":\"" + reason + "\"}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new InitiateTransferException("Invalid transfer details"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, errorResponse ->
                        Mono.error(new ExternalApiException("Paystack is unable to create transfer recipient due to server error"))
                )
                .bodyToMono(InitiateTransferResponse.class).block();

        return response;
    }

    private String generateTransferReference() {
        return UUID.randomUUID().toString();
    }
}
