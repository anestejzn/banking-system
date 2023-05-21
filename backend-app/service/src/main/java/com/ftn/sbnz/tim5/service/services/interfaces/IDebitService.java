package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.Debit;

public interface IDebitService {

    Debit save(Debit debit);

    void delete(Long debitId);
}
