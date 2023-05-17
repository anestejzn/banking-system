package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

public interface IAccountTypeService {

    AccountType getAccountTypeByName(String name) throws EntityNotFoundException;
}
