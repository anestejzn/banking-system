package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.response.AccountTypeResponse;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account-types")
public class AccountTypeController {

    @Autowired
    private IAccountTypeService accountTypeService;

    @GetMapping("/all-types")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountTypeResponse> getAll() {

        return accountTypeService.getAll();
    }

}
