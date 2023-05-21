package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DebitResponse {

    private Long id;
    private DebitType debitType;
    private double totalAmount;
    private LocalDateTime debitDate;
    private double monthlyInterest;
    private double monthlyAmount;
    private int paymentPeriod;
    private Status debitStatus;
    private Long accountId;

    public DebitResponse(Long id, DebitType debitType, double totalAmount, LocalDateTime debitDate, double monthlyInterest, double monthlyAmount, int paymentPeriod, Status debitStatus, Long accountId) {
        this.id = id;
        this.debitType = debitType;
        this.totalAmount = totalAmount;
        this.debitDate = debitDate;
        this.monthlyInterest = monthlyInterest;
        this.monthlyAmount = monthlyAmount;
        this.paymentPeriod = paymentPeriod;
        this.debitStatus = debitStatus;
        this.accountId = accountId;
    }

    public DebitResponse(Debit debit) {
        this.id = debit.getId();
        this.debitType = debit.getDebitType();
        this.totalAmount = debit.getTotalAmount();
        this.debitDate = debit.getDebitDate();
        this.monthlyInterest = debit.getMonthlyInterest();
        this.monthlyAmount = debit.getMonthlyAmount();
        this.paymentPeriod = debit.getPaymentPeriod();
        this.debitStatus = debit.getDebitStatus();
        this.accountId = debit.getAccount().getId();
    }


}
