package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.*;
import com.ftn.sbnz.tim5.model.enums.AccountStatus;
import com.ftn.sbnz.tim5.model.enums.EmployeeStatus;
import com.ftn.sbnz.tim5.service.dto.response.ClientResponse;
import com.ftn.sbnz.tim5.service.exception.*;
import com.ftn.sbnz.tim5.service.repository.ClientRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

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
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException, EntityNotFoundException, InvalidTermsOfAgreementException {
        checkValidityOfDataForRegistration(password, confirmPassword, email);
        checkRetireeTermsOfAgreement(termsOfPIOFondAgreement);

        Role role = roleService.getRoleByName(ROLE_CLIENT);
        AccountType accountType = accountTypeService.getAccountTypeByName(accountTypeName);
        Employer employer = employerService.getEmployerByName(PIO_FOND);
        verificationService.create(email);
        Account account = accountService.createNewAccountObject(accountType);

        return new ClientResponse(clientRepository.save(
                new Client(
                        email, getHash(password), name, surname, role, streetName, streetNumber, postCode,
                        city, dateOfBirth, EmployeeStatus.RETIREE, employer, account, monthlyIncome, AccountStatus.PENDING
                )
        ));
    }

    private void checkRetireeTermsOfAgreement(boolean termsOfPIOFondAgreement) throws InvalidTermsOfAgreementException {
        if (!termsOfPIOFondAgreement) {
            throw new InvalidTermsOfAgreementException("You must confirm that you are a member of PIO fond.");
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
    ) throws PasswordsDoNotMatchException, EntityAlreadyExistsException, EntityNotFoundException, IOException, MailCannotBeSentException {
        checkValidityOfDataForRegistration(password, confirmPassword, email);

        Role role = roleService.getRoleByName(ROLE_CLIENT);
        AccountType accountType = accountTypeService.getAccountTypeByName(accountTypeName);
        Employer employer = employerService.getEmployerByName(employerName);
        verificationService.create(email);
        Account account = accountService.createNewAccountObject(accountType);

        return new ClientResponse(clientRepository.save(
                new Client(
                        email, getHash(password), name, surname, role, streetName, streetNumber, postCode,
                        city, dateOfBirth, EmployeeStatus.EMPLOYED, employer, startedWorking, account, monthlyIncome, AccountStatus.PENDING
                )
        ));
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
