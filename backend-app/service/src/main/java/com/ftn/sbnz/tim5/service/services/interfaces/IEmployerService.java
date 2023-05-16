package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.Employer;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

public interface IEmployerService {
    Employer getEmployerByName(String name) throws EntityNotFoundException;
}
