package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> getClientByEmail(String email);
}
