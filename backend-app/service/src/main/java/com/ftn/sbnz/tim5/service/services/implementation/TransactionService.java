package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.model.enums.TransactionType;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.NotEnoughMoneyException;
import com.ftn.sbnz.tim5.service.repository.TransactionRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.ITransactionService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        return new TransactionResponse(save(transaction));
    }
}
