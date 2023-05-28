package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.model.enums.TransactionType;
import com.ftn.sbnz.tim5.service.controller.CashCreditController;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.InvalidCreditCardName;
import com.ftn.sbnz.tim5.service.services.interfaces.ICreditCardService;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.Constants.*;
import static com.ftn.sbnz.tim5.service.util.KieSessionHelper.createKieSessionFromDRL;

@Service
public class CreditCardService implements ICreditCardService {
    private final KieContainer kieContainer;
    private final KieSession kSession;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    public CreditCardService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("creditCardKsession");
    }

    @Override
    public boolean buyNewCreditCard(Long clientId, String creditCardName) throws InvalidCreditCardName, EntityNotFoundException {
        checkCreditCardName(creditCardName);
        Client client = clientService.getClientById(clientId);
        double amount = getAmount(creditCardName);
        CardType cardType = getCardType(creditCardName);

        Transaction transaction = new Transaction(amount, LocalDateTime.now(), false, BANK_NAME, TransactionType.CREDIT_CARD_TRANSACTION, cardType, Status.PENDING);
        transaction.setAccount(client.getAccount());

        //1. Se proverava da li se moze izdati kartica klijentu
        kSession.getAgenda().getAgendaGroup("credit-card-rejection").setFocus();
        kSession.insert(transaction);
        kSession.insert(client);
        kSession.insert(client.getAccount().getAccountType());
        kSession.insert(client.getAccount());
        kSession.fireAllRules();
        System.out.println(transaction.getStatus());

        kSession.getAgenda().getAgendaGroup("credit-card-rejection").clear();

        if (transaction.getStatus() != Status.REJECTED) {
            System.out.println(client.getAccount().getApplicantScore());
            //2. Dodeljuju se applicant score poeni
            KieSession kieSessionMonthlyInterestTemplate = getApplicantScorePointsTemplateKieSession();
            kieSessionMonthlyInterestTemplate.insert(transaction);
            kieSessionMonthlyInterestTemplate.insert(client.getAccount());
            kieSessionMonthlyInterestTemplate.insert(client);
            kieSessionMonthlyInterestTemplate.fireAllRules();
            System.out.println(client.getAccount().getApplicantScore());
        }

        return completePayment(transaction, client, amount, cardType);
    }

    private boolean completePayment(Transaction transaction, Client client, double amount, CardType cardType) {
        if (transaction.getStatus() == Status.PENDING) {
            transaction.setStatus(Status.ACTIVE);
            client.getAccount().setTotalBalance(client.getAccount().getTotalBalance() - amount);
            client.getAccount().getCards().add(cardType);

            transactionService.save(transaction);
            clientService.save(client);

            return true;
        } else {
            transaction.setStatus(Status.REJECTED);
            transactionService.save(transaction);
            clientService.save(client);

            return false;
        }
    }

    private KieSession getApplicantScorePointsTemplateKieSession(){
        InputStream template = CashCreditController.class.getResourceAsStream("/rules/credit_cards/applicant_score_points_credit_cards.drt");
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"VISA", "1"},
                new String[]{"VISA_PLATINUM", "5"},
                new String[]{"AMERICAN_EXPRESS", "10"},
        });

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);


        return createKieSessionFromDRL(drl);
    }

    private static void checkCreditCardName(String creditCardName) throws InvalidCreditCardName {
        if (!creditCardName.equalsIgnoreCase(VISA) && !creditCardName.equalsIgnoreCase(PLATINUM)
        && !creditCardName.equalsIgnoreCase(AMEX)) {
            throw new InvalidCreditCardName("Invalid credit card name");
        }
    }

    private static double getAmount(String cardName) throws InvalidCreditCardName {
        switch (cardName) {
            case VISA:
                return VISA_AMOUNT;
            case PLATINUM:
                return PLATINUM_AMOUNT;
            case AMEX:
                return AMEX_AMOUNT;
        }

        throw new InvalidCreditCardName("Invalid credit card name");
    }

    private static CardType getCardType(String cardName) throws InvalidCreditCardName {
        switch (cardName) {
            case VISA:
                return CardType.VISA;
            case PLATINUM:
                return CardType.VISA_PLATINUM;
            case AMEX:
                return CardType.AMERICAN_EXPRESS;
        }

        throw new InvalidCreditCardName("Invalid credit card name");
    }

}
