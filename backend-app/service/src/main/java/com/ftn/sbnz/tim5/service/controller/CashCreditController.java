package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import com.ftn.sbnz.tim5.model.enums.Status;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cash-credit")
public class CashCreditController {

    private final KieContainer kieContainer;

    @Autowired
    public CashCreditController(KieContainer kieContainer){
        this.kieContainer = kieContainer;
    }

    @GetMapping("/proba")
    public void proba(){
        InputStream template = CashCreditController.class.getResourceAsStream("/rules/cash_credits/reject_cash_credit_request_employee.drt");

        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"0", "25000", "15"},
                new String[]{"25001", "30000", "35"},
                new String[]{"30001", "40000", "50"},
                new String[]{"40001", "60000", "55"},
                new String[]{"60001", "500000", "60"},
        });

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        System.out.println(drl);

        KieSession ksession = createKieSessionFromDRL(drl);
        Role role = new Role();
        Employer employer = new Employer();

        AccountType accountType = new AccountType();
        Account account = new Account("1234567891234", LocalDateTime.now(), accountType, 500, 1);
        account.setId(1L);
        Debit debit = new Debit(DebitType.CASH_CREDIT, 50000, LocalDateTime.now(), 0, 3000, 21, Status.PENDING, account );
        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 40000, LocalDateTime.now(), 0, 6000, 21, Status.REJECTED, account );
        Debit debit2 = new Debit(DebitType.CASH_CREDIT, 40000, LocalDateTime.now(), 0, 6000, 21, Status.ACTIVE, account );
        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", role, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(22), EmployeeStatus.EMPLOYED,employer, account, 26000, Status.ACTIVE);
        account.getDebits().add(debit1);
        account.getDebits().add(debit2);
        ksession.insert(accountType);
        ksession.insert(account);
        ksession.insert(debit);
        ksession.insert(debit1);
        ksession.insert(client);
        ksession.fireAllRules();

        System.out.println(debit.getDebitStatus());
    }

    private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }

    @GetMapping("/proba2")
    public void proba2(){
        Role role = new Role();
        Employer employer = new Employer();

        AccountType accountType1 = new AccountType();
        accountType1.setName("Premium");
        Account account = new Account("1234567891234", LocalDateTime.now(), accountType1, 500, 1);
        account.setId(1L);
        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 50000, LocalDateTime.now(), 3, 3000, 60, Status.PENDING, account );

        Debit debit2 = new Debit(DebitType.CASH_CREDIT, 40000, LocalDateTime.now(), 0, 6000, 21, Status.ACTIVE, account );
        Debit debit3 = new Debit(DebitType.CASH_CREDIT, 40000, LocalDateTime.now(), 0, 6000, 21, Status.ACTIVE, account );
        Debit debit4 = new Debit(DebitType.CASH_CREDIT, 40000, LocalDateTime.now(), 0, 6000, 21, Status.ACTIVE, account );
        Debit debit5 = new Debit(DebitType.CASH_CREDIT, 40000, LocalDateTime.now(), 0, 6000, 21, Status.ACTIVE, account );
        account.getDebits().add(debit2);
        account.getDebits().add(debit3);
        account.getDebits().add(debit4);
        account.getDebits().add(debit5);
//      client is under 20
//      Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", role, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(18), EmployeeStatus.EMPLOYED,employer, account, 28000, Status.ACTIVE);
        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", role, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(22), EmployeeStatus.EMPLOYED,employer, account, 28000, Status.ACTIVE);
        account.setApplicantScore(10);
        account.getDebits().add(debit1);
        account.getCards().add(CardType.AMERICAN_EXPRESS);

        KieSession kieSession = kieContainer.newKieSession("cashCreditKsession");
        kieSession.insert(account);
        kieSession.insert(accountType1);
        kieSession.insert(debit1);
        kieSession.insert(client);

        kieSession.fireAllRules();

        System.out.println(debit1.getDebitStatus());
    }
}
