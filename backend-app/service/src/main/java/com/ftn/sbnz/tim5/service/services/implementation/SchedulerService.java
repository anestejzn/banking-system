package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountService;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class SchedulerService {

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
    public SchedulerService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("cashCreditKsession");
    }

    public void getMoneyForDebits(){
        List<Client> clientList = clientService.getAllClients();
        for(Client client : clientList){
            kSession.insert(client);
            kSession.insert(client.getAccount());
            kSession.insert(client.getAccount().getAccountType());
        }
        kSession.getAgenda().getAgendaGroup("get-money").setFocus();
        kSession.getAgenda().getAgendaGroup("penalty-group").setFocus();
        kSession.getAgenda().getAgendaGroup("client-suspicious").setFocus();
        kSession.fireAllRules();
        System.out.println("lalla");

        kSession.getAgenda().getAgendaGroup("get-money").clear();
        kSession.getAgenda().getAgendaGroup("penalty-group").clear();
        kSession.getAgenda().getAgendaGroup("client-suspicious").clear();

        kSession.fireAllRules();

        for(Client client : clientList) {
            clientService.save(client);
            accountService.save(client.getAccount());
        }
    }
}
