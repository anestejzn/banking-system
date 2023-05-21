package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.controller.CashCreditController;
import com.ftn.sbnz.tim5.service.dto.response.DebitResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountService;
import com.ftn.sbnz.tim5.service.services.interfaces.ICashCreditRequestService;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.KieSessionHelper.createKieSessionFromDRL;

@Service
public class CashCreditRequestService implements ICashCreditRequestService {
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
    public CashCreditRequestService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("cashCreditKsession");
    }
    //prvo preko template izracuna monthly interest
    //onda racuna umanjenje ako postoji -pravila
    //onda se racuna monthlyAmount
    //onda pravila za odbijanje
    public DebitResponse processCashCreditRequest(Long clientId, double amount, int paymentPeriod) throws EntityNotFoundException {
        Client client = clientService.getClientById(clientId);
        Debit debit = new Debit(DebitType.CASH_CREDIT, amount, LocalDateTime.now(), 0, 0, paymentPeriod, Status.PENDING, client.getAccount());

        KieSession kieSessionMonthlyInterestTemplate = getMonthlyInterestTemplateKieSession();
        kieSessionMonthlyInterestTemplate.insert(debit);
        kieSessionMonthlyInterestTemplate.insert(client.getAccount());
        kieSessionMonthlyInterestTemplate.insert(client);
        kieSessionMonthlyInterestTemplate.fireAllRules();
        kieSessionMonthlyInterestTemplate.dispose();

        kSession.getAgenda().getAgendaGroup("monthly-interest").setFocus();
        kSession.fireAllRules();

        KieSession kieSessionRejectRequestTemplate = getRejectRequestTemplateKieSession();
        kieSessionRejectRequestTemplate.insert(debit);
        kieSessionRejectRequestTemplate.insert(client);
        kieSessionRejectRequestTemplate.fireAllRules();
        kieSessionRejectRequestTemplate.dispose();

        System.out.println(debit.getMonthlyInterest());

        if(debit.getDebitStatus().equals(Status.PENDING)){
            double monthlyAmount = calculateMonthlyAmount(paymentPeriod, amount, debit.getMonthlyInterest());
            System.out.println("amount: " + monthlyAmount);
            debit.setMonthlyAmount(Double.valueOf(decfor.format(monthlyAmount)));
            kSession.getAgenda().getAgendaGroup("monthly-interest").clear();
            kSession.insert(client.getAccount().getAccountType());
            kSession.insert(client.getEmployer());
            kSession.fireAllRules();

            if(debit.getDebitStatus().equals(Status.PENDING)){
                debit.setDebitStatus(Status.ACTIVE);
            }
        }

        Debit savedDebit = debitService.save(debit);
        Account account = client.getAccount();
        account.getDebits().add(savedDebit);
        accountService.save(account);

        System.out.println(debit.getDebitStatus());

        return new DebitResponse(debit);
    }
    private KieSession getRejectRequestTemplateKieSession(){
        InputStream template = CashCreditController.class.getResourceAsStream("/rules/cash_credits/reject_cash_credit_request_employee.drt");

        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"0", "25000", "15"},
                new String[]{"25001", "30000", "25"},
                new String[]{"30001", "40000", "35"},
                new String[]{"40001", "60000", "40"},
                new String[]{"60001", "500000", "45"},
        });

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);


        return createKieSessionFromDRL(drl);
    }

    private KieSession getMonthlyInterestTemplateKieSession(){
        InputStream template = CashCreditController.class.getResourceAsStream("/rules/cash_credits/monthly_interest_template.drt");
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"0", "12", "0", "0", "2"},
                new String[]{"0", "12", "0", "20", "2.3"},
                new String[]{"13", "23", "0", "0", "2.5"},
                new String[]{"13", "23", "1", "20", "3.8"},
                new String[]{"24", "36", "0", "2", "3"},
                new String[]{"24", "36", "3", "20", "4.1"},
                new String[]{"37", "72", "3", "20", "4.5"},
        });

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);


        return createKieSessionFromDRL(drl);
    }

    private double calculateMonthlyAmount(int paymentPeriod, double totalAmount, double monthlyInterest){
        double x = totalAmount * (monthlyInterest/100);
        double amountWithInterest = totalAmount + x;

        return amountWithInterest/paymentPeriod;
    }




}
