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
    private double firstMonthlyIncome;
    @NotNull(message = "Amount can not be null")
    private double secondMonthlyIncome;
    @NotNull(message = "Amount can not be null")
    private double thirdMonthlyIncome;

    public OverdraftRequest(Long clientId, double firstMonthlyIncome, double secondMonthlyIncome, double thirdMonthlyIncome) {
        this.clientId = clientId;
        this.firstMonthlyIncome = firstMonthlyIncome;
        this.secondMonthlyIncome = secondMonthlyIncome;
        this.thirdMonthlyIncome = thirdMonthlyIncome;
    }
}
