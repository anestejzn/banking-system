import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.*;
import com.ftn.sbnz.tim5.service.controller.CashCreditController;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.rule.Agenda;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {

    private Role EMPTY_ROLE = new Role();
    private Employer EMPTY_EMPLOYER = new Employer();
    private AccountType EMPTY_ACC_TYPE = new AccountType();

    @Test
    @DisplayName("T1 - Should set right applicant points based on card type")
    public void applicantScoreBasedOnCardTransactionTemplateTest() {
        InputStream template = CashCreditController.class.getResourceAsStream("/rules/credit_cards/applicant_score_points_credit_cards.drt");

        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"VISA", "1"},
                new String[]{"VISA_PLATINUM", "5"},
                new String[]{"AMERICAN_EXPRESS", "10"},
        });

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        KieSession ksession = createKieSessionFromDRL(drl);

        //double amount, LocalDateTime transactionDate, boolean income, String otherSide, TransactionType transactionType, CardType boughtCardType
        Transaction t1 = new Transaction(2500.0, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.VISA_PLATINUM, Status.PENDING);
        Transaction t2 = new Transaction(10000.0, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        Account a2 = new Account("1221", LocalDateTime.now(), new AccountType());

        a1.setId(1L);
        t1.setAccount(a1);

        a2.setId(2L);
        t2.setAccount(a2);

        ksession.insert(t1);
        ksession.insert(t2);
        ksession.insert(a1);
        ksession.insert(a2);

        int counter = ksession.fireAllRules();
        assertEquals(2, counter);
        assertEquals(a1.getApplicantScore(), 5);
        assertEquals(a2.getApplicantScore(), 10);
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

    @Test
    @DisplayName("T2 - should reject transaction, client already has American Express card")
    public void shouldRejectTransactionClientAlreadyHasAmericanExpress() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
        Agenda agenda = kieSession.getAgenda();
        agenda.getAgendaGroup("credit-card-rejection").setFocus();

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        a1.getCards().add(CardType.AMERICAN_EXPRESS);   //dodaje se american express

        Transaction t1 = new Transaction(10000, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);
        t1.setAccount(a1);

        kieSession.insert(a1);
        kieSession.insert(t1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(1, ruleCount);
    }

    @Test
    @DisplayName("T3 - should reject transaction, client does not have Platinum Account Type")
    public void shouldRejectTransactionClientDoesNotHavePlatinumAcc() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
        Agenda agenda = kieSession.getAgenda();
        agenda.getAgendaGroup("credit-card-rejection").setFocus();

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        AccountType accountType = new AccountType();

        accountType.setName("Regular");
        a1.setAccountType(accountType);

        Transaction t1 = new Transaction(10000, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);
        t1.setAccount(a1);

        kieSession.insert(a1);
        kieSession.insert(t1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(1, ruleCount);
    }

    @Test
    @DisplayName("T4 - should reject transaction, client does not have enough money")
    public void shouldRejectTransactionClientNoEnoughMoney() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
        kieSession.getAgenda().getAgendaGroup("credit-card-rejection").setFocus();

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        AccountType accountType = new AccountType();

        accountType.setName("Platinum");
        a1.setAccountType(accountType);

        Transaction t1 = new Transaction(10000, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);
        t1.setAccount(a1);
        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED, EMPTY_EMPLOYER, a1, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(a1);
        kieSession.insert(t1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(1, ruleCount);
    }

    @Test
    @DisplayName("T5 - should reject transaction, client has too low monthly income")
    public void shouldRejectTransactionClientSalaryTooLow() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
        kieSession.getAgenda().getAgendaGroup("credit-card-rejection").setFocus();

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        AccountType accountType = new AccountType();

        accountType.setName("Platinum");
        a1.setAccountType(accountType);
        a1.setTotalBalance(15000);

        Transaction t1 = new Transaction(10000, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);
        t1.setAccount(a1);
        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED, EMPTY_EMPLOYER, a1, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(a1);
        kieSession.insert(t1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(1, ruleCount);
    }

    @Test
    @DisplayName("T6 - should reject transaction, client has less than 1 applicant score")
    public void shouldRejectTransactionClientHasLessThanOneAS() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
        Agenda agenda = kieSession.getAgenda();
        agenda.getAgendaGroup("credit-card-rejection").setFocus();

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        AccountType accountType = new AccountType();
        a1.setTotalBalance(15000);

        accountType.setName("Platinum");
        a1.setAccountType(accountType);
        a1.setApplicantScore(0);

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED, EMPTY_EMPLOYER, a1, 280000, Status.ACTIVE);

        Transaction t1 = new Transaction(10000, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);
        t1.setAccount(a1);

        kieSession.insert(client);
        kieSession.insert(a1);
        kieSession.insert(t1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(1, ruleCount);
    }

    @Test
    @DisplayName("T7 - should reject transaction, client has one penalty")
    public void shouldRejectTransactionClientHasOnePenalty() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
        Agenda agenda = kieSession.getAgenda();
        agenda.getAgendaGroup("credit-card-rejection").setFocus();

        Account a1 = new Account("1231", LocalDateTime.now(), new AccountType());
        AccountType accountType = new AccountType();
        a1.setTotalBalance(15000);

        accountType.setName("Platinum");
        a1.setAccountType(accountType);
        a1.setApplicantScore(2);

        Transaction t1 = new Transaction(10000, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS, Status.PENDING);
        t1.setAccount(a1);

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED, EMPTY_EMPLOYER, a1, 280000, Status.ACTIVE);
        ClientPenalty cp = new ClientPenalty(new Date(), "Penalty!");
        cp.setClient(client);

        kieSession.insert(cp);
        kieSession.insert(a1);
        kieSession.insert(t1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(1, ruleCount);
    }

}
