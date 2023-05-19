import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.AccountType;
import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.CardType;
import com.ftn.sbnz.tim5.model.enums.TransactionType;
import com.ftn.sbnz.tim5.service.controller.CashCreditController;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {

    @Test
    @DisplayName("Should set right applicant points based on card type")
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
        Transaction t1 = new Transaction(2500.0, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.VISA_PLATINUM);
        Transaction t2 = new Transaction(10000.0, LocalDateTime.now(), false, "BankingSystem DOO", TransactionType.CREDIT_CARD_TRANSACTION, CardType.AMERICAN_EXPRESS);

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

}
