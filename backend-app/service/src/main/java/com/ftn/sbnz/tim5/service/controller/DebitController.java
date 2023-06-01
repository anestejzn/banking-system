package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.request.ReportRequest;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.UnableToPerformActionException;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/debit")
public class DebitController {

    @Autowired
    private IDebitService debitService;

    @PostMapping("report")
    @ResponseStatus(HttpStatus.OK)
    public ReportResponse getReportStatistics(@RequestBody @Valid ReportRequest request) throws EntityNotFoundException, UnableToPerformActionException {

        return debitService.getReportStatistics(
          request.getClientId(),
          request.getReportType(),
          request.isShowAll(),
          request.getStartDate(),
          request.getEndDate()
        );
    }

    @DeleteMapping("/{debitId}")
    public void cancelDebitRequest(@PathVariable Long debitId){
        debitService.delete(debitId);
    }

    @GetMapping("/accept/{debitId}")
    public void acceptDebitRequest(@PathVariable Long debitId) throws EntityNotFoundException {
        debitService.accept(debitId);
    }
}
