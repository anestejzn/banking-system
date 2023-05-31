package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.ReportType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.UnableToPerformActionException;
import com.ftn.sbnz.tim5.service.repository.DebitRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ftn.sbnz.tim5.service.util.Helper.getTypeFromReport;

@Service
public class DebitService implements IDebitService {

    @Autowired
    private DebitRepository debitRepository;

    @Autowired
    private IClientService clientService;

    @Override
    public Debit save(Debit debit) {
        return debitRepository.save(debit);
    }

    @Override
    public void delete(Long debitId) {
        debitRepository.deleteById(debitId);
    }

    @Override
    public ReportResponse getReportStatistics(Long clientId,
                                              ReportType reportType,
                                              boolean showAll,
                                              LocalDateTime startDate,
                                              LocalDateTime endDate
    ) throws EntityNotFoundException, UnableToPerformActionException {
        checkDataValidity(reportType);

        return showAll ? showStatisticsForAll(startDate, endDate, reportType)
                : showStatisticsForClient(startDate, endDate, reportType, clientId);
    }

    private void checkDataValidity(ReportType reportType) throws UnableToPerformActionException {
        if (reportType == ReportType.CREDIT_CARD_REPORT) {
            throw new UnableToPerformActionException("Wrong report type.");
        }
    }

    private ReportResponse showStatisticsForClient(LocalDateTime startDate, LocalDateTime endDate, ReportType reportType, Long clientId) throws EntityNotFoundException {
        Client client = clientService.getClientById(clientId);
        List<Debit> debits = client.getAccount().getDebits();
        Stream<Debit> filteredDebits = debits.stream().filter(debit -> debit.getDebitType() == getTypeFromReport(reportType) && debit.getDebitDate().isAfter(startDate) && debit.getDebitDate().isBefore(endDate));


        return calculateNumOfDebits(filteredDebits.collect(Collectors.toList()));
    }

    private ReportResponse showStatisticsForAll(LocalDateTime startDate, LocalDateTime endDate, ReportType reportType) {
        List<Debit> debits = reportType == ReportType.CASH_CREDIT_REPORT ? debitRepository.getAllCashCredit(startDate, endDate)
                                            : debitRepository.getAllOverdraft(startDate, endDate);


        return calculateNumOfDebits(debits);
    }

    public static ReportResponse calculateNumOfDebits(List<Debit> debits) {
        int numOfActive = 0;
        int numOfPending = 0;
        int numOfRejected = 0;

        for (Debit d : debits) {
            if (d.getDebitStatus() == Status.ACTIVE) {
                numOfActive += 1;
            } else if (d.getDebitStatus() == Status.PENDING) {
                numOfPending += 1;
            } else {
                numOfRejected += 1;
            }
        }

        return new ReportResponse(numOfActive, numOfPending, numOfRejected);
    }
}
