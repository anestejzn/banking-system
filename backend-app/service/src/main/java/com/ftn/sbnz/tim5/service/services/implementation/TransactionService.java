package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;
import com.ftn.sbnz.tim5.service.repository.TransactionRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftn.sbnz.tim5.service.dto.response.TransactionResponse.fromTransactionsToResponses;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> getFilteredTransactions(Long accountId, Status parameter) {

        return fromTransactionsToResponses(transactionRepository.getFilteredTransactions(accountId, parameter));
    }
}
