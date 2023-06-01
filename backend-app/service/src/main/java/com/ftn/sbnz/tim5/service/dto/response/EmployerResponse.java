package com.ftn.sbnz.tim5.service.dto.response;

import com.ftn.sbnz.tim5.model.Employer;
import com.ftn.sbnz.tim5.model.enums.EmployerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EmployerResponse {

    private String name;
    private String pib;
    private Boolean NBS_certified;
    private EmployerStatus employerStatus;
    private LocalDateTime startedOperating;

    public EmployerResponse(String name, String pib, Boolean NBS_certified, EmployerStatus employerStatus, LocalDateTime startedOperating) {
        this.name = name;
        this.pib = pib;
        this.NBS_certified = NBS_certified;
        this.employerStatus = employerStatus;
        this.startedOperating = startedOperating;
    }

    public EmployerResponse(Employer employer){
        name = employer.getName();
        pib = employer.getPib();
        NBS_certified = employer.getNBS_certified();
        employerStatus = employer.getEmployerStatus();
        startedOperating = employer.getStartedOperating();
    }
}
