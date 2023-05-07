package com.ftn.sbnz.tim5.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="employer")
@Setter
@Getter
@NoArgsConstructor
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="pib", nullable = false, unique = true)
    private String pib;

    public Employer(String name, String pib) {
        this.name = name;
        this.pib = pib;
    }
}
