package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    @Query("select at from AccountType at where at.name=?1")
    Optional<AccountType> getAccountTypeByName(String name);
}
