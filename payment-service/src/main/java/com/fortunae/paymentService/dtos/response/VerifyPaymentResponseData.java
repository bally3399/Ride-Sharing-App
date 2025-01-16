package com.fortunae.paymentService.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyPaymentResponseData {
    private Long id;
    private String status;
    private String reference;
    private BigDecimal amount;
    @JsonProperty("gateway_response")
    private String gatewayResponse;
    @JsonProperty("paid_at")
    private LocalDateTime paidAt;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    private String currency;
    @JsonProperty("ip_address")
    private String ipAddress;
//    private Customer customer;
}
