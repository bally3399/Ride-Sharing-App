package com.fortunae.paymentService.service;


import com.fortunae.paymentService.dtos.response.CreateTransferRecipientResponse;
import com.fortunae.paymentService.dtos.response.InitialisePaymentResponse;
import com.fortunae.paymentService.dtos.response.InitiateTransferResponse;
import com.fortunae.paymentService.dtos.response.VerifyPaymentResponse;

import java.math.BigDecimal;

public interface PaymentGatewayOutputPort {

    InitialisePaymentResponse initialisePayment(String email, BigDecimal amount);

    VerifyPaymentResponse verifyPayment(String reference);

    CreateTransferRecipientResponse createTransferRecipient(String accountNumber, String bankCode);

    InitiateTransferResponse initiateTransfer(String source, BigDecimal amount, String recipient, String reason);
}
