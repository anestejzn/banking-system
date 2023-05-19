package com.ftn.sbnz.tim5.model;

import com.ftn.sbnz.tim5.model.enums.EmployerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="employer")
@Setter
@Getter
@NoArgsConstructor
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="pib", nullable = false, unique = true)
    private String pib;

    @Column(name = "nbs_certified", nullable = false)
    private Boolean NBS_certified = false;

    @Column(name = "employer_status")
    private EmployerStatus employerStatus;

    @Column(name = "started_operating")
    private LocalDateTime startedOperating;

    public Employer(String name,
                    String pib,
                    Boolean NBS_certified
    ) {
        this.name = name;
        this.pib = pib;
        this.NBS_certified = NBS_certified;
        this.employerStatus = EmployerStatus.ACTIVE;
        this.startedOperating = LocalDateTime.now();
    }

    public Employer(String name,
                    String pib,
                    Boolean NBS_certified,
                    EmployerStatus employerStatus,
                    LocalDateTime startedOperating
    ) {
        this.name = name;
        this.pib = pib;
        this.NBS_certified = NBS_certified;
        this.employerStatus = employerStatus;
        this.startedOperating = startedOperating;
    }
}
