package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.service.dto.response.DebitResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;

public interface IOverdraftService {

    DebitResponse processOverdraftRequest(Long clientId, double firstMonthlyAmount, double secondMonthlyAmount, double thirdMonthlyAmount) throws EntityNotFoundException;
}
