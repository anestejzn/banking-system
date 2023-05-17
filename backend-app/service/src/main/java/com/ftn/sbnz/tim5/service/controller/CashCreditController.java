package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.Status;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cash-credit")
public class CashCreditController {

    @GetMapping("/proba")
    public void proba(){
        InputStream template = CashCreditController.class.getResourceAsStream("/rules/cash_credits/monthly_interest_template.drt");

        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"0", "12", "0", "0", "2"},
                new String[]{"13", "23", "0", "0", "2.5"},
                new String[]{"24", "36", "0", "2", "3"},
                new String[]{"37", "72", "3", "5", "4"},
        });

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        System.out.println(drl);

        KieSession ksession = createKieSessionFromDRL(drl);
        AccountType accountType = new AccountType();
        Account account = new Account("1234567891234", LocalDateTime.now(), accountType, 500, 1);
        Debit debit = new Debit(DebitType.CASH_CREDIT, 50000, LocalDateTime.now(), 0, 0, 21, Status.PENDING, account );

        ksession.insert(accountType);
        ksession.insert(account);
        ksession.insert(debit);
        ksession.fireAllRules();

        System.out.println(debit.getMonthlyInterest());
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
}
