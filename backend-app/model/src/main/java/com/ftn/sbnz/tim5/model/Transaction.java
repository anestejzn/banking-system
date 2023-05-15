package com.ftn.sbnz.tim5.model;

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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="amount", nullable = false)
    private double amount;
    @Column(name="transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    @Column(name="income", nullable = false)
    private boolean income; //da li je prihod ili rashod, prihod-true; rashod-false
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name="other_side", nullable = false)
    private String otherSide; //ko je uplatilac ili kome se placa

    public Transaction(double amount, LocalDateTime transactionDate, boolean income, String otherSide) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.income = income;
        this.otherSide = otherSide;
    }
}
