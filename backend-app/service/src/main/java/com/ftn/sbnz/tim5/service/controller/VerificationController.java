package com.ftn.sbnz.tim5.service.controller;


import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.MailCannotBeSentException;
import com.ftn.sbnz.tim5.service.services.implementation.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static com.ftn.sbnz.tim5.service.util.ErrorMessageConstants.WRONG_VERIFY_HASH;

@RestController
@RequestMapping("verify")
public class VerificationController {
    @Autowired
    private VerificationService verificationService;

    @PostMapping("/send-code-again")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @NotNull(message = WRONG_VERIFY_HASH) @RequestBody String verifyHash) throws EntityNotFoundException, MailCannotBeSentException, IOException, EntityNotFoundException, MailCannotBeSentException {
        this.verificationService.generateNewSecurityCode(verifyHash);
    }


}
