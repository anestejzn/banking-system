package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.response.AccountResponse;
import com.ftn.sbnz.tim5.service.dto.response.ClientResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.ftn.sbnz.tim5.service.util.Constants.MISSING_ID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccountById(@Valid @NotNull(message = MISSING_ID) @PathVariable Long id)
            throws EntityNotFoundException
    {

        return new AccountResponse(accountService.getAccountById(id));
    }

}
