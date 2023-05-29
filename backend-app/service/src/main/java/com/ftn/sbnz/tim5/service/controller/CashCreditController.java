package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.request.CashCreditRequest;
import com.ftn.sbnz.tim5.service.dto.response.DebitResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.services.implementation.CashCreditRequestService;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cash-credit")
public class CashCreditController {


    @Autowired
    private CashCreditRequestService cashCreditRequestService;

    @PostMapping("/send-cash-credit-request")
    public DebitResponse sendCashCreditRequest(@RequestBody CashCreditRequest cashCreditRequest) throws EntityNotFoundException {
        return cashCreditRequestService.processCashCreditRequest(cashCreditRequest.getClientId(), cashCreditRequest.getAmount(), cashCreditRequest.getPaymentPeriod());
    }

}
