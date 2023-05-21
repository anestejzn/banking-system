package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.service.dto.response.DebitResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

public interface ICashCreditRequestService {

    DebitResponse processCashCreditRequest(Long clientId, double amount, int paymentPeriod) throws EntityNotFoundException;

}
