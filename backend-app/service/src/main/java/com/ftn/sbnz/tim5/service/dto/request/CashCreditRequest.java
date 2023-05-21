package com.ftn.sbnz.tim5.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CashCreditRequest {

    @NotNull(message = "Client id can not be null")
    private Long clientId;
    @NotNull(message = "Amount can not be null")
    private double amount;
    @NotNull(message = "Payment period can not be null")
    private int paymentPeriod;

    public CashCreditRequest(Long clientId, double amount, int paymentPeriod) {
        this.clientId = clientId;
        this.amount = amount;
        this.paymentPeriod = paymentPeriod;
    }
}
