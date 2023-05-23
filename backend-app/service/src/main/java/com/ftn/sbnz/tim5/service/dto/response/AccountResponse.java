package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.CardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private LocalDateTime accountDate;
    private double totalBalance;
    private double applicantScore;
    private AccountTypeResponse accountType;
    private List<Debit> debits = new LinkedList<>();
    private List<Transaction> transactions = new LinkedList<>();
    private List<CardType> cards = new LinkedList<>();

    public AccountResponse(Long id,
                           String accountNumber,
                           LocalDateTime accountDate,
                           double totalBalance,
                           double applicantScore,
                           AccountTypeResponse accountType,
                           List<Debit> debits,
                           List<Transaction> transactions,
                           List<CardType> cards
    ) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountDate = accountDate;
        this.totalBalance = totalBalance;
        this.applicantScore = applicantScore;
        this.accountType = accountType;
        this.debits = debits;
        this.transactions = transactions;
        this.cards = cards;
    }

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.accountDate = account.getAccountDate();
        this.totalBalance = account.getTotalBalance();
        this.applicantScore = account.getApplicantScore();
        this.accountType = new AccountTypeResponse(account.getAccountType());
        this.debits = account.getDebits();
        this.transactions = account.getTransactions();
        this.cards = account.getCards();
    }

    public static List<AccountResponse> fromAccountsToResponse(List<Account> accounts) {
        List<AccountResponse> responses = new LinkedList<>();
        accounts.forEach(acc -> {
            responses.add(new AccountResponse(acc));
        });

        return responses;
    }

}
