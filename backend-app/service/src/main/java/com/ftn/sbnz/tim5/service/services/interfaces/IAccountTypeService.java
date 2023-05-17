package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.service.dto.response.AccountTypeResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

import java.util.List;

public interface IAccountTypeService {

    AccountType getAccountTypeByName(String name) throws EntityNotFoundException;

    List<AccountTypeResponse> getAll();
}
