package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> getClientByEmail(String email);

    @Query("select c from Client c where c.id=?1")
    Optional<Client> getClientById(Long id);

    @Query("select distinct c from Client c where c.accountStatus=1 and c.verified=true")
    List<Client> getPendingClients();

    @Query("select c from Client c")
    List<Client> getAll();

    @Query("select distinct c from Client c where c.accountStatus=0 and c.verified=true")
    List<Client> getVerifiedActiveClients();
}
