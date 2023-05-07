package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name="employeeStatus", nullable = false)
    private EmployeeStatus employeeStatus;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Account> accounts;
    @OneToOne
    @JoinColumn(name = "employer", referencedColumnName = "id")
    private Employer employer;

}