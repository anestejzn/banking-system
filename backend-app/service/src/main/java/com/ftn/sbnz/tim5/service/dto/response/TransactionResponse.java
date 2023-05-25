package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.model.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private double amount;
    private LocalDateTime transactionDate;
    private boolean income;
    private String otherSide;
    private TransactionType transactionType;
    private Status status;
    private CardType boughtCardType;

    public TransactionResponse(Long id,
                               double amount,
                               LocalDateTime transactionDate,
                               boolean income, String otherSide,
                               TransactionType transactionType,
                               Status status,
                               CardType boughtCardType
    ) {
        this.id = id;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.income = income;
        this.otherSide = otherSide;
        this.transactionType = transactionType;
        this.status = status;
        this.boughtCardType = boughtCardType;
    }

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
        this.income = transaction.isIncome();
        this.otherSide = transaction.getOtherSide();
        this.transactionType = transaction.getTransactionType();
        this.status = transaction.getStatus();
        this.boughtCardType = transaction.getBoughtCardType();
    }

    public static List<TransactionResponse> fromTransactionsToResponses(List<Transaction> transactions) {
        List<TransactionResponse> responses = new LinkedList<>();
        transactions.forEach(transaction -> {
            responses.add(new TransactionResponse(transaction));
        });

        return responses;
    }

}
