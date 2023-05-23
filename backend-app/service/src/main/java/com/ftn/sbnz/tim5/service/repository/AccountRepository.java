package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.accountNumber=?1")
    Optional<Account> getAccountByNumber(String accNum);

    @Query("select distinct a from Account a where a.id=?1")
    Optional<Account> getAccountById(Long id);

}
