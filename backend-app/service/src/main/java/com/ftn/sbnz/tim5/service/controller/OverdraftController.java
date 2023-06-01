package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.request.CashCreditRequest;
import com.ftn.sbnz.tim5.service.dto.request.OverdraftRequest;
import com.ftn.sbnz.tim5.service.dto.response.DebitResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.services.implementation.OverdraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overdraft")
public class OverdraftController {

    @Autowired
    private OverdraftService overdraftService;

    @PostMapping("/send-overdraft-request")
    public DebitResponse sendOverdraftRequest(@RequestBody OverdraftRequest overdraftRequest) throws EntityNotFoundException {

        return overdraftService.processOverdraftRequest(
                overdraftRequest.getClientId(),
                overdraftRequest.getFirstMonthlyIncome(),
                overdraftRequest.getSecondMonthlyIncome(),
                overdraftRequest.getThirdMonthlyIncome());
    }
}
