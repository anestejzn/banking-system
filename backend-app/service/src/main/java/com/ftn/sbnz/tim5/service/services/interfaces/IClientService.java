package com.ftn.sbnz.tim5.service.services.interfaces;


import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.service.dto.response.AccountResponse;
import com.ftn.sbnz.tim5.service.dto.response.ClientResponse;
import com.ftn.sbnz.tim5.service.exception.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IClientService {
    Client getClient(String email) throws EntityNotFoundException;

    boolean activateAccount(String userEmail) throws EntityNotFoundException;

    ClientResponse createRetiree(String email, String name, String surname, String password, String confirmPassword, LocalDateTime dateOfBirth, double monthlyIncome, String accountTypeName, String city, String postCode, String streetName, String streetNumber, boolean termsOfPIOFondAgreement) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException, EntityNotFoundException, InvalidTermsOfAgreementException, InvalidDateOfBirthException;

    ClientResponse createEmployedClient(String email, String name, String surname, String password, String confirmPassword, LocalDateTime dateOfBirth, double monthlyIncome, String accountTypeName, String city, String postCode, String streetName, String streetNumber, String employerName, LocalDateTime startedWorking) throws PasswordsDoNotMatchException, EntityAlreadyExistsException, EntityNotFoundException, IOException, MailCannotBeSentException, InvalidDateOfBirthException;

    List<ClientResponse> getPendingClients();

    boolean acceptRegistrationRequest(Long id) throws EntityNotFoundException;

    Client getClientById(Long id) throws EntityNotFoundException;

    boolean rejectRegistrationRequest(Long id) throws EntityNotFoundException;

    List<Client> getAllClients();

    Client save(Client client);

    List<ClientResponse> getVerifiedClients();
}
