package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.TransactionResponse;

import java.util.List;

public interface ITransactionService {
    List<TransactionResponse> getFilteredTransactions(Long accountId, Status parameter);
}
