package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.Employer;
import com.ftn.sbnz.tim5.service.dto.response.EmployerBasicResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

import java.util.List;

public interface IEmployerService {
    Employer getEmployerByName(String name) throws EntityNotFoundException;

    List<EmployerBasicResponse> getAll();
}
