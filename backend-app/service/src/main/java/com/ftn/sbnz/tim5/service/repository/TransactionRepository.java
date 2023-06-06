package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.Transaction;
import com.ftn.sbnz.tim5.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.account.id=?1 and t.status=?2 order by t.transactionDate desc")
    List<Transaction> getFilteredTransactions(Long accountId, Status parameter);

    @Query("select t from Transaction t")
    List<Transaction> getAllCreditCardTransactions();
}
