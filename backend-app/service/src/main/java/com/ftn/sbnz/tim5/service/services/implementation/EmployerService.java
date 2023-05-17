package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.model.Employer;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.repository.EmployerRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IEmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService implements IEmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    public Employer getEmployerByName(String name) throws EntityNotFoundException {
        return employerRepository.getEmployerByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Employer is not found."));
    }

}
