package com.ftn.sbnz.tim5.service.repository;


import com.ftn.sbnz.tim5.model.RegistrationVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<RegistrationVerification, Long> {

    Optional<RegistrationVerification> getRegistrationVerificationsById(Long id);
    Optional<RegistrationVerification> getRegistrationVerificationsByHashedId(String id);

}
