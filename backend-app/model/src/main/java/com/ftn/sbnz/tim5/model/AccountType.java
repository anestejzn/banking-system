package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.CardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="account_type")
@Setter
@Getter
@NoArgsConstructor
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false, unique = true)
    private String name;
    @Column(name="currency", nullable = false)
    private String currency;
    @Column(name="monthly_subscription", nullable = false)
    private double monthlySubscription;
    @Column(name="overdraft", nullable = false)
    private boolean overdraft;
    @Column(name="cash_credit_limit", nullable = false)
    private double cashCreditLimit;
    @ElementCollection(targetClass = CardType.class)
    @JoinTable(name = "card_enum", joinColumns = @JoinColumn(name = "account_type_id"))
    @Column(name = "cards", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<CardType> cards = new LinkedList<>();

    public AccountType(String name, String currency, double monthlySubscription, boolean overdraft, double cashCreditLimit, List<CardType> cards) {
        this.name = name;
        this.currency = currency;
        this.monthlySubscription = monthlySubscription;
        this.overdraft = overdraft;
        this.cashCreditLimit = cashCreditLimit;
        this.cards = cards;
    }

}
