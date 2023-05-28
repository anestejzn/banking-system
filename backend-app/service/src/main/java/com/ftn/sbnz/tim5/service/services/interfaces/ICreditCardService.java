package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.InvalidCreditCardName;

public interface ICreditCardService {


    boolean buyNewCreditCard(Long clientId, String creditCardName) throws InvalidCreditCardName, EntityNotFoundException;
}
