package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.model.enums.CardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountTypeResponse {

    private Long id;
    private String name;
    private String currency;
    private double monthlySubscription;
    private boolean overdraft;
    private double cashCreditLimit;
    private List<CardType> cards = new LinkedList<>();

    public AccountTypeResponse(Long id,
                               String name,
                               String currency,
                               double monthlySubscription,
                               boolean overdraft,
                               double cashCreditLimit,
                               List<CardType> cards
    ) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.monthlySubscription = monthlySubscription;
        this.overdraft = overdraft;
        this.cashCreditLimit = cashCreditLimit;
        this.cards = cards;
    }

    public AccountTypeResponse(AccountType accountType) {
        this.id = accountType.getId();
        this.name = accountType.getName();
        this.currency = accountType.getCurrency();
        this.monthlySubscription = accountType.getMonthlySubscription();
        this.overdraft = accountType.isOverdraft();
        this.cashCreditLimit = accountType.getCashCreditLimit();
        this.cards = accountType.getCards();
    }

    public static List<AccountTypeResponse> fromAccountTypesToResponse(List<AccountType> types) {
        List<AccountTypeResponse> responses = new LinkedList<>();
        types.forEach(type -> {
            responses.add(new AccountTypeResponse(type));
        });

        return responses;
    }
}
