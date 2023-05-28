package com.ftn.sbnz.tim5.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.ftn.sbnz.tim5.service.util.Constants.MISSING_ID;

@Getter
@Setter
public class NewCreditCardRequest {

    @NotNull(message = MISSING_ID)
    private Long clientId;

    @NotBlank(message = "Credit card name cannot be empty.")
    private String creditCardName;

    public NewCreditCardRequest(Long clientId, String creditCardName) {
        this.clientId = clientId;
        this.creditCardName = creditCardName;
    }
}
