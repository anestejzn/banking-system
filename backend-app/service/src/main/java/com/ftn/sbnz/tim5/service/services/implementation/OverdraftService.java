package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.DebitResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountService;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import com.ftn.sbnz.tim5.service.services.interfaces.IOverdraftService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Service
public class OverdraftService implements IOverdraftService {
    private final KieContainer kieContainer;
    private final KieSession kSession;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IDebitService debitService;

    @Autowired
    private IAccountService accountService;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Autowired
    public OverdraftService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("cashCreditKsession");
    }
    @Override
    public DebitResponse processOverdraftRequest(Long clientId, double firstMonthlyAmount, double secondMonthlyAmount, double thirdMonthlyAmount) throws EntityNotFoundException {
        Client client = clientService.getClientById(clientId);
        EmployeeStatus employeeStatus = client.getEmployeeStatus();
        int paymentPeriod = 24;
        if(employeeStatus.equals(EmployeeStatus.RETIREE)){
            paymentPeriod = 12;
        }

        System.out.println((firstMonthlyAmount + secondMonthlyAmount + thirdMonthlyAmount) / 3);
        double amount = Double.parseDouble(decfor.format((firstMonthlyAmount + secondMonthlyAmount + thirdMonthlyAmount) / 3));
        Debit debit = new Debit(DebitType.OVERDRAFT, amount, LocalDateTime.now(), 0, 0, paymentPeriod, Status.PENDING, client.getAccount());
        //1. Proverava se suspicious
        kSession.getAgenda().getAgendaGroup("client-suspicious").setFocus();
        kSession.insert(debit);
        kSession.insert(client);
        kSession.insert(client.getAccount());
        kSession.insert(client.getAccount().getAccountType());
        kSession.fireAllRules();
        System.out.println(debit.getDebitStatus());
        System.out.println(debit.getTotalAmount());

        //2. Osnovna pravila za overdraft
        kSession.getAgenda().getAgendaGroup("client-suspicious").clear();
        kSession.getAgenda().getAgendaGroup("overdraft-rejection").setFocus();
//        kSession.insert(debit);
//        kSession.insert(client);
//        kSession.insert(client.getAccount());
//        kSession.insert(client.getAccount().getAccountType());
//        kSession.insert(client.getEmployer());
        kSession.fireAllRules();
        System.out.println(debit.getDebitStatus());

        if(debit.getDebitStatus().equals(Status.PENDING)){
            debit.setDebitStatus(Status.ACTIVE);
        }

        Debit savedDebit = debitService.save(debit);
        Account account = client.getAccount();
        account.getDebits().add(savedDebit);
        accountService.save(account);

        System.out.println(debit.getDebitStatus());

        return new DebitResponse(debit);
    }
}
