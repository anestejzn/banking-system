package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.repository.AccountRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.Constants.MAX_ACCOUNT_NUM;
import static com.ftn.sbnz.tim5.service.util.Constants.MIN_ACCOUNT_NUM;
import static com.ftn.sbnz.tim5.service.util.Helper.generateRandomNumber;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountByNumber(String accNum) throws EntityNotFoundException {
        return accountRepository.getAccountByNumber(accNum)
                .orElseThrow(() -> new EntityNotFoundException("Account is not found."));
    }

    @Override
    public Account createNewAccountObject(AccountType accountType) {

        return new Account(
                String.valueOf(generateRandomNumber(MIN_ACCOUNT_NUM, MAX_ACCOUNT_NUM)),
                LocalDateTime.now(),
                accountType
        );
    }

    @Override
    public Account save(Account account) {

        return accountRepository.save(account);
    }

}
