package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.service.repository.DebitRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitService implements IDebitService {

    @Autowired
    private DebitRepository debitRepository;

    @Override
    public Debit save(Debit debit) {
        return debitRepository.save(debit);
    }

    @Override
    public void delete(Long debitId) {
        debitRepository.deleteById(debitId);
    }
}
