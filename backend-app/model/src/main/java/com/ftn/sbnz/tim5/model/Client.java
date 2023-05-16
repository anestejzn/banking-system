package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.AccountStatus;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="client")
@Setter
@Getter
@NoArgsConstructor
public class Client extends User{

    @Column(name="street_name", nullable = false)
    private String streetName;

    @Column(name="street_number", nullable = false)
    private String streetNumber;

    @Column(name="post_code", nullable = false)
    private String postCode;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="date_of_birth", nullable = false)
    private LocalDateTime dateOfBirth;

    @Column(name="employee_status", nullable = false)
    private EmployeeStatus employeeStatus;

    @Column(name="verified", nullable = false)
    private boolean verified = false;

    @Column(name="account_status", nullable = false)
    private AccountStatus accountStatus;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    private Employer employer;

    @Column(name="started_working")
    private LocalDateTime startedWorking;

    @Column(name="monthly_income")
    private double monthlyIncome;

    public Client(
            String email,
            String password,
            String name,
            String surname,
            Role role,
            String streetName,
            String streetNumber,
            String postCode,
            String city,
            LocalDateTime dateOfBirth,
            EmployeeStatus employeeStatus,
            Employer employer,
            Account account,
            double monthlyIncome,
            AccountStatus accountStatus
    ) {
        super(email, password, name, surname, role);
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.employeeStatus = employeeStatus;
        this.employer = employer;
        this.account = account;
        this.monthlyIncome = monthlyIncome;
        this.accountStatus = accountStatus;
    }

    //ovde ima startedWorking, gore nema
    public Client(
            String email,
            String password,
            String name,
            String surname,
            Role role,
            String streetName,
            String streetNumber,
            String postCode,
            String city,
            LocalDateTime dateOfBirth,
            EmployeeStatus employeeStatus,
            Employer employer,
            LocalDateTime startedWorking,
            Account account,
            double monthlyIncome,
            AccountStatus accountStatus
    ) {
        super(email, password, name, surname, role);
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.employeeStatus = employeeStatus;
        this.employer = employer;
        this.startedWorking = startedWorking;
        this.account = account;
        this.monthlyIncome = monthlyIncome;
        this.accountStatus = accountStatus;
    }
}
