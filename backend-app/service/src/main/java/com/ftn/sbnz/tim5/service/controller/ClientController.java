package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.request.EmployedClientRegistrationRequest;
import com.ftn.sbnz.tim5.service.dto.request.RetireeRegistrationRequest;
import com.ftn.sbnz.tim5.service.dto.response.ClientResponse;
import com.ftn.sbnz.tim5.service.exception.*;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private IClientService clientService;


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

}
