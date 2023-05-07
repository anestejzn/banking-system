package com.ftn.sbnz.tim5.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_type", referencedColumnName = "id")
    private AccountType accountType;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Debit> debits;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Account(String accountNumber, LocalDateTime accountDate, AccountType accountType, Client client) {
        this.accountNumber = accountNumber;
        this.accountDate = accountDate;
        this.accountType = accountType;
        this.client = client;
    }
}
