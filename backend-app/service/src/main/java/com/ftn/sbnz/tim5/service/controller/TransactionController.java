package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.request.RegularTransactionRequest;
import com.ftn.sbnz.tim5.service.dto.request.ReportRequest;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.NotEnoughMoneyException;
import com.ftn.sbnz.tim5.service.exception.UnableToPerformActionException;
import com.ftn.sbnz.tim5.service.services.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("report")
    @ResponseStatus(HttpStatus.OK)
    public ReportResponse getReportStatistics(@RequestBody @Valid ReportRequest request) throws EntityNotFoundException, UnableToPerformActionException {

        return transactionService.getReportStatistics(
                request.getClientId(),
                request.getReportType(),
                request.isShowAll(),
                request.getStartDate(),
                request.getEndDate()
        );
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createRegularTransaction(@RequestBody @Valid RegularTransactionRequest request) throws EntityNotFoundException, NotEnoughMoneyException {

        return transactionService.createRegularTransaction(
          request.getClientId(),
          request.getAmount(),
          request.getOtherSide()
        );
    }

}
