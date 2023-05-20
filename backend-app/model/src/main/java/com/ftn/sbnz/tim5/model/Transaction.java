package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.model.enums.TransactionType;
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

    @Column(name="transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name="bought_card_type")
    private CardType boughtCardType;

    public Transaction(double amount,
                       LocalDateTime transactionDate,
                       boolean income,
                       String otherSide,
                       TransactionType transactionType,
                       Status status
    ) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.income = income;
        this.otherSide = otherSide;
        this.transactionType = transactionType;
        this.status = status;
    }

    public Transaction(double amount,
                       LocalDateTime transactionDate,
                       boolean income,
                       String otherSide,
                       TransactionType transactionType,
                       CardType boughtCardType,
                       Status status
    ) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.income = income;
        this.otherSide = otherSide;
        this.transactionType = transactionType;
        this.boughtCardType = boughtCardType;
        this.status = status;
    }
}
