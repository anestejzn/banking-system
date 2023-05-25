package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;
import com.ftn.sbnz.tim5.service.services.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/{accountId}/{parameter}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> getFilteredTransactions(@PathVariable Long accountId,@PathVariable Status parameter) {

        return transactionService.getFilteredTransactions(accountId, parameter);
    }

}
