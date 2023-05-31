package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Debit;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DebitRepository extends JpaRepository<Debit, Long> {

    @Query("select distinct d from Debit d where d.debitDate >= ?1 and d.debitDate <= ?2 and d.debitType=0")
    List<Debit> getAllCashCredit(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select distinct d from Debit d where d.debitDate >= ?1 and d.debitDate <= ?2 and d.debitType=1")
    List<Debit> getAllOverdraft(LocalDateTime startDate, LocalDateTime endDate);
}
