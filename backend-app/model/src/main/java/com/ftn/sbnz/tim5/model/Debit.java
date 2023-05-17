package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="debit")
@Setter
@Getter
@NoArgsConstructor
public class Debit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "debit_type", nullable = false)
    private DebitType debitType;
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    @Column(name = "debit_date", nullable = false)
    private LocalDateTime debitDate;
    @Column(name = "monthly_interest")
    private double monthlyInterest;
    @Column(name = "monthly_amount")
    private double monthlyAmount;
    @Column(name = "payment_period", nullable = false)
    private int paymentPeriod;
    @Column(name = "debit_status", nullable = false)
    private Status debitStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Debit(DebitType debitType, double totalAmount, LocalDateTime debitDate, double monthlyInterest, double monthlyAmount, int paymentPeriod, Status debitStatus, Account account) {
        this.debitType = debitType;
        this.totalAmount = totalAmount;
        this.debitDate = debitDate;
        this.monthlyInterest = monthlyInterest;
        this.monthlyAmount = monthlyAmount;
        this.paymentPeriod = paymentPeriod;
        this.debitStatus = debitStatus;
        this.account = account;
    }
}
