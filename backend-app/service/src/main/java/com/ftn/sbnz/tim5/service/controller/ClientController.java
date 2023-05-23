package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.request.EmployedClientRegistrationRequest;
import com.ftn.sbnz.tim5.service.dto.request.RetireeRegistrationRequest;
import com.ftn.sbnz.tim5.service.dto.response.AccountResponse;
import com.ftn.sbnz.tim5.service.dto.response.ClientResponse;
import com.ftn.sbnz.tim5.service.exception.*;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static com.ftn.sbnz.tim5.service.util.Constants.MISSING_ID;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("all-pending-clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponse> getPendingClients() {

        return clientService.getPendingClients();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponse getClientById(@Valid @NotNull(message = MISSING_ID) @PathVariable Long id)
            throws EntityNotFoundException
    {

        return new ClientResponse(clientService.getClientById(id));
    }

    @PostMapping(path = "register-retired-client")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse registerRetired(@Valid @RequestBody RetireeRegistrationRequest request)
            throws PasswordsDoNotMatchException, EntityAlreadyExistsException, IOException, MailCannotBeSentException, EntityNotFoundException, InvalidTermsOfAgreementException, InvalidDateOfBirthException {

        return clientService.createRetiree(
          request.getEmail(),
          request.getName(),
          request.getSurname(),
          request.getPassword(),
          request.getConfirmPassword(),
          request.getDateOfBirth(),
          request.getMonthlyIncome(),
          request.getAccountTypeName(),
          request.getCity(),
          request.getPostCode(),
          request.getStreetName(),
          request.getStreetNumber(),
          request.isTermsOfPIOFondAgreement()
        );
    }

    @PostMapping(path = "register-employed-client")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse registerEmployed(@Valid @RequestBody EmployedClientRegistrationRequest request)
            throws PasswordsDoNotMatchException, EntityAlreadyExistsException, IOException, MailCannotBeSentException, EntityNotFoundException, InvalidDateOfBirthException {

        return clientService.createEmployedClient(
                request.getEmail(),
                request.getName(),
                request.getSurname(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getDateOfBirth(),
                request.getMonthlyIncome(),
                request.getAccountTypeName(),
                request.getCity(),
                request.getPostCode(),
                request.getStreetName(),
                request.getStreetNumber(),
                request.getEmployerName(),
                request.getStartedWorking()
        );
    }

    @PutMapping("/accept-registration-request/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean acceptRegistrationRequest(@PathVariable @Valid @NotNull(message = MISSING_ID) Long id) throws EntityNotFoundException {

        return clientService.acceptRegistrationRequest(id);
    }

    @PutMapping("/reject-registration-request/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean rejectRegistrationRequest(@PathVariable @Valid @NotNull(message = MISSING_ID) Long id) throws EntityNotFoundException {

        return clientService.rejectRegistrationRequest(id);
    }

}
