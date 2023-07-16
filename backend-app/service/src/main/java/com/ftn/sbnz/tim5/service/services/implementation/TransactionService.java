package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.ReportType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.model.enums.TransactionType;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.NotEnoughMoneyException;
import com.ftn.sbnz.tim5.service.exception.UnableToPerformActionException;
import com.ftn.sbnz.tim5.service.repository.TransactionRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.ITransactionService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ftn.sbnz.tim5.service.dto.response.TransactionResponse.fromTransactionsToResponses;


@Service
public class TransactionService implements ITransactionService {
    private final KieContainer kieContainer;
    private final KieSession kSession;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IClientService clientService;

    @Autowired
    public TransactionService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("cashCreditKsession");
    }

    @Override
    public List<TransactionResponse> getFilteredTransactions(Long accountId, Status parameter) {

        return fromTransactionsToResponses(transactionRepository.getFilteredTransactions(accountId, parameter));
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public TransactionResponse createRegularTransaction(Long clientId, Double amount, String otherSide) throws EntityNotFoundException, NotEnoughMoneyException {
        Client client = clientService.getClientById(clientId);
        double totalBalance = client.getAccount().getTotalBalance();

        if (totalBalance < amount) {
            throw new NotEnoughMoneyException("You don't have enough money, check your balance.");
        }

        Transaction transaction = new Transaction(amount, LocalDateTime.now(), false, otherSide, TransactionType.REGULAR_TRANSACTION, null, Status.ACTIVE);
        transaction.setAccount(client.getAccount());

        client.getAccount().getTransactions().add(transaction);
        client.getAccount().setTotalBalance(totalBalance - amount);

        kSession.getAgenda().getAgendaGroup("client-suspicious").setFocus();
        kSession.insert(client);
        kSession.insert(client.getAccount());
        kSession.fireAllRules();//client-suspicious

        clientService.save(client);

        return new TransactionResponse(save(transaction));
    }

    @Override
    public ReportResponse getReportStatistics(Long clientId,
                                              ReportType reportType,
                                              boolean showAll,
                                              LocalDateTime startDate,
                                              LocalDateTime endDate
    ) throws EntityNotFoundException, UnableToPerformActionException {
        checkDataValidity(reportType);

        return showStatisticsForAll(startDate, endDate, showAll, clientId);

    }

    private void checkDataValidity(ReportType reportType) throws UnableToPerformActionException {
        if (reportType != ReportType.CREDIT_CARD_REPORT) {
            throw new UnableToPerformActionException("Wrong report type.");
        }
    }

    private ReportResponse showStatisticsForAll(LocalDateTime startDate, LocalDateTime endDate, boolean showAll, Long clientId) throws EntityNotFoundException {
        List<Transaction> transactions = new LinkedList<>();

        if(showAll){
            transactions = transactionRepository.getAllCreditCardTransactions();
        }
        else{
            Client client = clientService.getClientById(clientId);
            transactions = client.getAccount().getTransactions();
        }

        kSession.insert(transactions);

        Long activeDebitsNum = 0L;
        Long pendingDebitsNum = 0L;
        Long rejectedDebitsNum = 0L;
        QueryResults results = kSession.getQueryResults("reportCreditCardsForAll", startDate, endDate, transactions.toArray());
        for (QueryResultsRow row : results) {
            rejectedDebitsNum = (Long) row.get("$numOfRejected");
            pendingDebitsNum = (Long) row.get("$numOfPending");
            activeDebitsNum = (Long) row.get("$numOfActive");
        }


        System.out.println("active" + activeDebitsNum);
        System.out.println("rejected" + rejectedDebitsNum);
        System.out.println("pending" + pendingDebitsNum);

        return new ReportResponse(activeDebitsNum, pendingDebitsNum, rejectedDebitsNum);
    }

}
