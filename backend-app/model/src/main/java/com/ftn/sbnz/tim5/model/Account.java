package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.CardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="account")
@Setter
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account_number", nullable = false)
    private String accountNumber;

    @Column(name="account_date", nullable=false)
    private LocalDateTime accountDate;

    @Column(name="total_balance", nullable=false)
    private double totalBalance;

    @Column(name="applicant_score", nullable=false)
    private double applicantScore;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_type_id", referencedColumnName = "id")
    private AccountType accountType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Debit> debits = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions = new LinkedList<>();

    @ElementCollection(targetClass = CardType.class)
    @JoinTable(name = "card_enum_additional", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "cards", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<CardType> cards = new LinkedList<>();


    public Account(String accountNumber, LocalDateTime accountDate, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountDate = accountDate;
        this.accountType = accountType;
        this.totalBalance = 0;
        this.applicantScore = 0;
    }

    public Account(String accountNumber, LocalDateTime accountDate, AccountType accountType, double totalBalance, int applicantScore) {
        this.accountNumber = accountNumber;
        this.accountDate = accountDate;
        this.accountType = accountType;
        this.totalBalance = totalBalance;
        this.applicantScore = applicantScore;
    }
}
