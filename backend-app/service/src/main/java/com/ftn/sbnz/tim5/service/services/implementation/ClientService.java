package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.AccountResponse;
import com.ftn.sbnz.tim5.service.dto.response.ClientResponse;
import com.ftn.sbnz.tim5.service.exception.*;
import com.ftn.sbnz.tim5.service.repository.ClientRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.ftn.sbnz.tim5.service.dto.response.ClientResponse.fromClientListToClientResponses;
import static com.ftn.sbnz.tim5.service.util.Constants.PIO_FOND;
import static com.ftn.sbnz.tim5.service.util.Constants.ROLE_CLIENT;
import static com.ftn.sbnz.tim5.service.util.Helper.getHash;
import static com.ftn.sbnz.tim5.service.util.Helper.passwordsDontMatch;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private IAccountTypeService accountTypeService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IEmployerService employerService;

    @Override
    public Client getClient(String email) throws EntityNotFoundException {
        return clientRepository.getClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User is not found."));
    }

    @Override
    public Client getClientById(Long id) throws EntityNotFoundException {
        return clientRepository.getClientById(id)
                .orElseThrow(() -> new EntityNotFoundException("User is not found."));
    }

    @Override
    public boolean rejectRegistrationRequest(Long id) throws EntityNotFoundException {
        Client client = this.getClientById(id);
        client.setAccountStatus(Status.REJECTED);
        clientRepository.save(client);

        return true;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.getAll();
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<ClientResponse> getVerifiedClients() {

        return fromClientListToClientResponses(clientRepository.getVerifiedActiveClients());
    }


    @Override
    public boolean activateAccount(String userEmail) throws EntityNotFoundException {
        Client client = this.getClient(userEmail);
        client.setVerified(true);
        clientRepository.save(client);

        return true;
    }

    @Override
    public ClientResponse createRetiree(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            LocalDateTime dateOfBirth,
            double monthlyIncome,
            String accountTypeName,
            String city,
            String postCode,
            String streetName,
            String streetNumber,
            boolean termsOfPIOFondAgreement
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException, EntityNotFoundException, InvalidTermsOfAgreementException, InvalidDateOfBirthException {
        checkValidityOfDataForRegistration(password, confirmPassword, email);
        checkRetireeTermsOfAgreement(termsOfPIOFondAgreement);
        checkDayOfBirthValidity(dateOfBirth);

        Role role = roleService.getRoleByName(ROLE_CLIENT);
        AccountType accountType = accountTypeService.getAccountTypeByName(accountTypeName);
        Employer employer = employerService.getEmployerByName(PIO_FOND);
        verificationService.create(email);
        Account account = accountService.createNewAccountObject(accountType);

        return new ClientResponse(clientRepository.save(
                new Client(
                        email, getHash(password), name, surname, role, streetName, streetNumber, postCode,
                        city, dateOfBirth, EmployeeStatus.RETIREE, employer, account, monthlyIncome, Status.PENDING
                )
        ));
    }

    private void checkRetireeTermsOfAgreement(boolean termsOfPIOFondAgreement) throws InvalidTermsOfAgreementException {
        if (!termsOfPIOFondAgreement) {
            throw new InvalidTermsOfAgreementException("You must confirm that you are a member of PIO fond.");
        }
    }

    private void checkDayOfBirthValidity(LocalDateTime dayOfBirth) throws InvalidDateOfBirthException {
        if (ChronoUnit.YEARS.between(dayOfBirth, LocalDateTime.now()) < 18) {
            throw new InvalidDateOfBirthException("You cannot open an account if you are younger than 18.");
        }
    }

    @Override
    public ClientResponse createEmployedClient(String email,
                                               String name,
                                               String surname,
                                               String password,
                                               String confirmPassword,
                                               LocalDateTime dateOfBirth,
                                               double monthlyIncome,
                                               String accountTypeName,
                                               String city,
                                               String postCode,
                                               String streetName,
                                               String streetNumber,
                                               String employerName,
                                               LocalDateTime startedWorking
    ) throws PasswordsDoNotMatchException, EntityAlreadyExistsException, EntityNotFoundException, IOException, MailCannotBeSentException, InvalidDateOfBirthException {
        checkValidityOfDataForRegistration(password, confirmPassword, email);
        checkDayOfBirthValidity(dateOfBirth);

        Role role = roleService.getRoleByName(ROLE_CLIENT);
        AccountType accountType = accountTypeService.getAccountTypeByName(accountTypeName);
        Employer employer = employerService.getEmployerByName(employerName);
        verificationService.create(email);
        Account account = accountService.createNewAccountObject(accountType);

        return new ClientResponse(clientRepository.save(
                new Client(
                        email, getHash(password), name, surname, role, streetName, streetNumber, postCode,
                        city, dateOfBirth, EmployeeStatus.EMPLOYED, employer, startedWorking, account, monthlyIncome, Status.PENDING
                )
        ));
    }

    public List<ClientResponse> getPendingClients() {

        return fromClientListToClientResponses(clientRepository.getPendingClients());
    }

    @Override
    public boolean acceptRegistrationRequest(Long id) throws EntityNotFoundException {
        Client client = this.getClientById(id);
        client.setAccountStatus(Status.ACTIVE);
        client.setVerified(true);
        clientRepository.save(client);

        return true;
    }

    private void checkValidityOfDataForRegistration(
            String password,
            String confirmPassword,
            String email
    ) throws PasswordsDoNotMatchException, EntityAlreadyExistsException {
        if (passwordsDontMatch(password, confirmPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.checkIfClientAlreadyExists(email)) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists.", email));
        }
    }

    private boolean checkIfClientAlreadyExists(String email) {
        Optional<Client> client = clientRepository.getClientByEmail(email);
        return client.isPresent();
    }

}
