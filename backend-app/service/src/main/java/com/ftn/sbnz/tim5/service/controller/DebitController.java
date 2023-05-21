package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debit")
public class DebitController {

    @Autowired
    private IDebitService debitService;

    @DeleteMapping("/{debitId}")
    public void cancelDebitRequest(@PathVariable Long debitId){
        debitService.delete(debitId);
    }
}
