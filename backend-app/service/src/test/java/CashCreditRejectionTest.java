import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.DebitType;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import com.ftn.sbnz.tim5.model.enums.EmployerStatus;
import com.ftn.sbnz.tim5.model.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashCreditRejectionTest {

    private Role EMPTY_ROLE = new Role();
    private Employer EMPTY_EMPLOYER = new Employer();
    private AccountType EMPTY_ACC_TYPE = new AccountType();

    @Test
    @DisplayName("T1 - Cash credit rejected, retiree is older than 65 and amount is >= 150K")
    public void shouldRejectDueToYearsAndAmount() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

        Account account = new Account("1234567891234", LocalDateTime.now(), EMPTY_ACC_TYPE, 500, 1);
        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.RETIREE,EMPTY_EMPLOYER, account, 28000, Status.ACTIVE);

        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 170000, LocalDateTime.now(), 0, 0, 60, Status.PENDING, account);

        kieSession.insert(client);
        kieSession.insert(debit1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(ruleCount, 1);
    }

    @Test
    @DisplayName("T2 - Cash credit rejected, retiree has total debt ratio greater than 30% of monthly income")
    public void shouldRejectRetireeDueToExceedingDebtAndIncomeRation() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

        Account account = new Account("1234567891234", LocalDateTime.now(), EMPTY_ACC_TYPE, 500, 1);

        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 10000, LocalDateTime.now(), 2, 2, 12, Status.PENDING, account);
        Debit debit2 = new Debit(DebitType.CASH_CREDIT, 30000, LocalDateTime.now(), 3, 4000, 12, Status.ACTIVE, account);
        Debit debit3 = new Debit(DebitType.CASH_CREDIT, 100000, LocalDateTime.now(), 3, 5000, 12, Status.ACTIVE, account);

        account.getDebits().add(debit2);
        account.getDebits().add(debit3);

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.RETIREE,EMPTY_EMPLOYER, account, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(debit1);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(ruleCount, 1);
    }

    @Test
    @DisplayName("T3 - Cash credit rejected, Employer is operating less than 12 months")
    public void shouldRejectCreditDueToEmployerShortOperating() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

        Account account = new Account("1234567891234", LocalDateTime.now(), EMPTY_ACC_TYPE, 500, 1);

        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 10000, LocalDateTime.now(), 2, 2, 12, Status.PENDING, account);
        Employer employer = new Employer("Apple", "123123", true, EmployerStatus.ACTIVE, LocalDateTime.now().minusMonths(5));

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED,employer, account, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(debit1);
        kieSession.insert(employer);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(ruleCount, 1);
    }

    @Test
    @DisplayName("T4 - Cash credit rejected, Employer is not registered on NBS web register")
    public void shouldRejectCreditDueToEmployerNotBeingRegisteredOnNBS() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

        Account account = new Account("1234567891234", LocalDateTime.now(), EMPTY_ACC_TYPE, 500, 1);

        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 10000, LocalDateTime.now(), 2, 2, 12, Status.PENDING, account);
        Employer employer = new Employer("Apple", "123123", false, EmployerStatus.ACTIVE, LocalDateTime.now().minusMonths(20));

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED,employer, account, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(debit1);
        kieSession.insert(employer);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(ruleCount, 1);
    }

    @Test
    @DisplayName("T5 - Cash credit rejected, Employer is blocked")
    public void shouldRejectCreditDueToEmployerBeingBlocked() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

        Account account = new Account("1234567891234", LocalDateTime.now(), EMPTY_ACC_TYPE, 500, 1);

        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 10000, LocalDateTime.now(), 2, 2, 12, Status.PENDING, account);
        Employer employer = new Employer("Apple", "123123", true, EmployerStatus.BLOCKED, LocalDateTime.now().minusMonths(20));

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED,employer, account, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(debit1);
        kieSession.insert(employer);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(ruleCount, 1);
    }

    @Test
    @DisplayName("T5 - Cash credit rejected, Employee is working less than 6 months at current Employer")
    public void shouldRejectCreditDueToEmployeeWorkingLessThanSixMonths() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kieContainer.getKieBase("cashCreditKbase");

        KieSessionConfiguration kieSessionConfiguration = KieServices.Factory.get().newKieSessionConfiguration();

        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

        Account account = new Account("1234567891234", LocalDateTime.now(), EMPTY_ACC_TYPE, 500, 1);

        Debit debit1 = new Debit(DebitType.CASH_CREDIT, 10000, LocalDateTime.now(), 2, 2, 12, Status.PENDING, account);
        Employer employer = new Employer("Apple", "123123", true, EmployerStatus.ACTIVE, LocalDateTime.now().minusMonths(20));

        Client client = new Client("ana@gmail.com", "12345", "Ana", "Anic", EMPTY_ROLE, "Kraljevacka", "10", "36000", "Kraljevo", LocalDateTime.now().minusYears(67), EmployeeStatus.EMPLOYED,employer, LocalDateTime.now().minusMonths(3), account, 28000, Status.ACTIVE);

        kieSession.insert(client);
        kieSession.insert(debit1);
        kieSession.insert(employer);

        int ruleCount = kieSession.fireAllRules();
        assertEquals(ruleCount, 1);
    }

}
