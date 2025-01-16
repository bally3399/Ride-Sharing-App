package com.fortunae.paymentService.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransferRecipientResponse {
    private boolean status;
    private String message;
    @JsonProperty("data")
    private CreateTransferRecipientResponseData data;
}


