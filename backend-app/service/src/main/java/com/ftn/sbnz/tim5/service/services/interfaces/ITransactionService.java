package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.NotEnoughMoneyException;

import java.util.List;

public interface ITransactionService {
    List<TransactionResponse> getFilteredTransactions(Long accountId, Status parameter);
    Transaction save(Transaction transaction);

    TransactionResponse createRegularTransaction(Long clientId, Double amount, String otherSide) throws EntityNotFoundException, NotEnoughMoneyException;
}
