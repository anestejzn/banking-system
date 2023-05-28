package com.ftn.sbnz.tim5.service.controller;

import com.ftn.sbnz.tim5.service.dto.request.NewCreditCardRequest;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.InvalidCreditCardName;
import com.ftn.sbnz.tim5.service.services.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private ICreditCardService creditCardService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public boolean buyNewCreditCard(@RequestBody @Valid NewCreditCardRequest request)
            throws EntityNotFoundException, InvalidCreditCardName
    {

        return creditCardService.buyNewCreditCard(
                request.getClientId(),
                request.getCreditCardName().toUpperCase(Locale.ROOT)
        );
    }


}
