package com.ftn.sbnz.tim5.service.services.implementation;

import com.ftn.sbnz.tim5.model.Account;
import com.ftn.sbnz.tim5.model.Client;
import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.ReportType;
import com.ftn.sbnz.tim5.model.enums.Status;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.UnableToPerformActionException;
import com.ftn.sbnz.tim5.service.repository.DebitRepository;
import com.ftn.sbnz.tim5.service.services.interfaces.IAccountService;
import com.ftn.sbnz.tim5.service.services.interfaces.IClientService;
import com.ftn.sbnz.tim5.service.services.interfaces.IDebitService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ftn.sbnz.tim5.service.util.Helper.getTypeFromReport;

@Service
public class DebitService implements IDebitService {

    private final KieContainer kieContainer;
    private final KieSession kSession;

    @Autowired
    private DebitRepository debitRepository;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    public DebitService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("cashCreditKsession");
    }

    @Override
    public Debit save(Debit debit) {
        return debitRepository.save(debit);
    }

    @Override
    public void delete(Long debitId) {
        debitRepository.deleteById(debitId);
    }

    @Override
    public void accept(Long debitId) throws EntityNotFoundException {
        Debit debit = getDebitById(debitId);
        Account account = debit.getAccount();
        account.setTotalBalance(account.getTotalBalance() + debit.getTotalAmount());
        accountService.save(account);
    }

    @Override
    public Debit getDebitById(Long id) throws EntityNotFoundException {
        return debitRepository.getDebitById(id)
                .orElseThrow(() -> new EntityNotFoundException("Debit is not found."));
    }

    @Override
    public ReportResponse getReportStatistics(Long clientId,
                                              ReportType reportType,
                                              boolean showAll,
                                              LocalDateTime startDate,
                                              LocalDateTime endDate
    ) throws EntityNotFoundException, UnableToPerformActionException {
        checkDataValidity(reportType);

        return showStatisticsForAll(startDate, endDate, reportType, showAll, clientId);
    }

    private void checkDataValidity(ReportType reportType) throws UnableToPerformActionException {
        if (reportType == ReportType.CREDIT_CARD_REPORT) {
            throw new UnableToPerformActionException("Wrong report type.");
        }
    }


    private ReportResponse showStatisticsForAll(LocalDateTime startDate, LocalDateTime endDate, ReportType reportType, boolean showAll, Long clientId) throws EntityNotFoundException {
        List<Debit> debits = new LinkedList<>();
        if(showAll){
            debits = debitRepository.getAllDebits();
        }
        else{
            Client client = clientService.getClientById(clientId);
            debits = client.getAccount().getDebits();
        }
//        for(Debit debit : debits){
//            kSession.insert(debit);
//        }
        kSession.insert(debits);

        Long activeDebitsNum = 0L;
        Long pendingDebitsNum = 0L;
        Long rejectedDebitsNum = 0L;
        QueryResults results =  reportType == ReportType.CASH_CREDIT_REPORT ? getCashCreditsForAll(startDate, endDate, debits) : getOverdraftsForAll(startDate, endDate, debits);
        for (QueryResultsRow row : results) {
            rejectedDebitsNum = (Long) row.get("$numOfRejected");
            pendingDebitsNum = (Long) row.get("$numOfPending");
            activeDebitsNum = (Long) row.get("$numOfActive");
        }

        return new ReportResponse(activeDebitsNum, pendingDebitsNum, rejectedDebitsNum);
    }

    private QueryResults getCashCreditsForAll(LocalDateTime startDate, LocalDateTime endDate, List<Debit> debits){
        return kSession.getQueryResults("reportCashCreditsForAll", startDate, endDate, debits.toArray());
    }

    private QueryResults getOverdraftsForAll(LocalDateTime startDate, LocalDateTime endDate, List<Debit> debits){
        return kSession.getQueryResults("reportOverdraftsForAll", startDate, endDate, debits.toArray());
    }


}
