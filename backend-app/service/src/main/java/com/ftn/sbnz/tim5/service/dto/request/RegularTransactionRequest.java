package com.ftn.sbnz.tim5.service.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.ftn.sbnz.tim5.service.util.Constants.MISSING_ID;

@Getter
@Setter
public class RegularTransactionRequest {

    @NotNull(message = MISSING_ID)
    private Long clientId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Other side is required.")
    private String otherSide;

    public RegularTransactionRequest(Long clientId, Double amount, String otherSide) {
        this.clientId = clientId;
        this.amount = amount;
        this.otherSide = otherSide;
    }
}
