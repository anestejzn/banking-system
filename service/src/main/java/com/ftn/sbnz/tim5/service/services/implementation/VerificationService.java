package com.ftn.sbnz.tim5.service.services.implementation;


import com.ftn.sbnz.tim5.model.RegistrationVerification;
import com.ftn.sbnz.tim5.service.dto.response.VerifyMailDTO;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.MailCannotBeSentException;
import com.ftn.sbnz.tim5.service.exception.WrongVerifyTryException;
import com.ftn.sbnz.tim5.service.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.ftn.sbnz.tim5.service.util.Constants.*;
import static com.ftn.sbnz.tim5.service.util.EmailConstants.FRONT_VERIFY_URL;
import static com.ftn.sbnz.tim5.service.util.Helper.*;


@Service
public class VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private EmailService emailService;

    public RegistrationVerification get(Long id) throws EntityNotFoundException {

        return verificationRepository.getRegistrationVerificationsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration verification is not found."));
    }

    public RegistrationVerification getByHashedId(String id) throws EntityNotFoundException {

        return verificationRepository.getRegistrationVerificationsByHashedId(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration verification is not found."));
    }

    public RegistrationVerification update(String verifyId, int securityCode) throws EntityNotFoundException, WrongVerifyTryException {
        RegistrationVerification verify = getByHashedId(verifyId);
        if (canVerify(String.valueOf(securityCode), verify)) {
            incrementNumOfTries(verify);
            this.saveChanges(verify, true);

            return verify;
        } else if (wrongCodeButHasTries(verify)){
            this.saveChanges(verify, incrementNumOfTries(verify) >= MAX_NUM_VERIFY_TRIES);

            throw new WrongVerifyTryException("Your security code is not accepted. Try again.");
        } else {
            saveChanges(verify, true);

            throw new WrongVerifyTryException("Your verification code is either expired or typed wrong 3 times. Reset code.");
        }
    }

    private void saveChanges(final RegistrationVerification verify, final boolean used) {
        verify.setUsed(used);
        verificationRepository.save(verify);
    }

    public boolean create(String email) throws IOException, MailCannotBeSentException {
        String salt = generateRandomString(SALT_LENGTH);
        int securityCode = generateSecurityCode();
        RegistrationVerification registrationVerification = new RegistrationVerification(
                getHash(String.valueOf(securityCode)),
                ZERO_FAILED_ATTEMPTS,
                email,
                LocalDateTime.now().plusMinutes(10),
                salt,
                generateHashForURL(salt + email)
        );

        verificationRepository.save(registrationVerification);
        this.sendVerificationEmail(new VerifyMailDTO(securityCode, registrationVerification.getHashedId()));

        return true;
    }

    public void generateNewSecurityCode(String verifyHash)
            throws EntityNotFoundException, IOException, MailCannotBeSentException
    {
        RegistrationVerification verify = getByHashedId(verifyHash);
        create(verify.getUserEmail());
        verificationRepository.delete(verify);
    }

    public void sendVerificationEmail(VerifyMailDTO verifyMailDTO)
            throws IOException, MailCannotBeSentException
    {
        emailService.sendVerificationMail(
                verifyMailDTO.getSecurityCode(),
                String.format("%s%s", FRONT_VERIFY_URL, verifyMailDTO.getHashId())
        );
    }

    private boolean isNotUsed(RegistrationVerification verification) {
        return !verification.isUsed();
    }

    private boolean hasTries(RegistrationVerification registrationVerification) {
        return registrationVerification.getFailedAttempts() < MAX_NUM_VERIFY_TRIES;
    }

    private int incrementNumOfTries(RegistrationVerification registrationVerification) {
        registrationVerification.setFailedAttempts(registrationVerification.getFailedAttempts() + 1);
//        verificationRepository.save(registrationVerification);

        return registrationVerification.getFailedAttempts();
    }

    private boolean checkSecurityCode(String securityCode, RegistrationVerification registrationVerification){

        return BCrypt.checkpw(securityCode, registrationVerification.getSecurityCode());
    }

    private boolean notExpired(RegistrationVerification registrationVerification) {

        return registrationVerification.getExpires().isAfter(LocalDateTime.now());
    }

    private boolean canVerify(String securityCode, RegistrationVerification registrationVerification) {
        return isNotUsed(registrationVerification) && hasTries(registrationVerification) && checkSecurityCode(securityCode, registrationVerification) && notExpired(registrationVerification);
    }

    private boolean wrongCodeButHasTries(RegistrationVerification registrationVerification) {return hasTries(registrationVerification) && isNotUsed(registrationVerification) && notExpired(registrationVerification); }
}

