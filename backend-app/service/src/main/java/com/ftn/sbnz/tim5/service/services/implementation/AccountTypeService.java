package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.service.dto.response.AccountTypeResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.repository.AccountTypeRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftn.sbnz.tim5.service.dto.response.AccountTypeResponse.fromAccountTypesToResponse;

@Service
public class AccountTypeService implements IAccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public AccountType getAccountTypeByName(String name) throws EntityNotFoundException {
        return accountTypeRepository.getAccountTypeByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Account Type is not found."));
    }

    @Override
    public List<AccountTypeResponse> getAll() {

        return fromAccountTypesToResponse(accountTypeRepository.getAll());
    }

}
