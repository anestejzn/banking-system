package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Employer;
import com.ftn.sbnz.tim5.model.enums.EmployerStatus;
import com.ftn.sbnz.tim5.service.dto.request.EmployerRequest;
import com.ftn.sbnz.tim5.service.dto.response.EmployerBasicResponse;
import com.ftn.sbnz.tim5.service.dto.response.EmployerResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.repository.EmployerRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IEmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.ftn.sbnz.tim5.service.dto.response.EmployerBasicResponse.fromEmployersWithoutPioFondToBasicResponses;

@Service
public class EmployerService implements IEmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public Employer getEmployerByName(String name) throws EntityNotFoundException {
        return employerRepository.getEmployerByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Employer is not found."));
    }

    @Override
    public List<EmployerBasicResponse> getAll() {

        return fromEmployersWithoutPioFondToBasicResponses(employerRepository.findAll());
    }

    @Override
    public EmployerResponse save(String name, String pib, LocalDateTime startedOperating) {
        Employer employer = employerRepository.save(
                new Employer(name, pib, true, EmployerStatus.ACTIVE, startedOperating));

        return new EmployerResponse(employer);
    }

}
