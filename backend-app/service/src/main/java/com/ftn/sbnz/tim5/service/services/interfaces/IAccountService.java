package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

public interface IAccountService {
    Account getAccountByNumber(String accNum) throws EntityNotFoundException;

    Account createNewAccountObject(AccountType accountType);
}
