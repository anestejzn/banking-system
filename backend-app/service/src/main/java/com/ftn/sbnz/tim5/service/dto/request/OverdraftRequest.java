package com.ftn.sbnz.tim5.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OverdraftRequest {
    @NotNull(message = "Client id can not be null")
    private Long clientId;
    @NotNull(message = "Amount can not be null")
    private double firstMonthlyAmount;
    @NotNull(message = "Amount can not be null")
    private double secondMonthlyAmount;
    @NotNull(message = "Amount can not be null")
    private double thirdMonthlyAmount;

    public OverdraftRequest(Long clientId, double firstMonthlyAmount, double secondMonthlyAmount, double thirdMonthlyAmount) {
        this.clientId = clientId;
        this.firstMonthlyAmount = firstMonthlyAmount;
        this.secondMonthlyAmount = secondMonthlyAmount;
        this.thirdMonthlyAmount = thirdMonthlyAmount;
    }
}
