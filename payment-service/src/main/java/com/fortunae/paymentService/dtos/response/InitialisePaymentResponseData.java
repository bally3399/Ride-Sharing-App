package com.fortunae.paymentService.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialisePaymentResponseData {
    @JsonProperty("authorization_url")
    private String authorizationUrl;
    @JsonProperty("access_code")
    private String accessCode;
    @JsonProperty("reference")
    private String reference;
}