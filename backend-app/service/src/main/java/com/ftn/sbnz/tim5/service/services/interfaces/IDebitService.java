package com.ftn.sbnz.tim5.service.services.interfaces;

import com.ftn.sbnz.tim5.model.Debit;
import com.ftn.sbnz.tim5.model.enums.ReportType;
import com.ftn.sbnz.tim5.service.dto.response.ReportResponse;
import com.ftn.sbnz.tim5.service.exception.EntityNotFoundException;
import com.ftn.sbnz.tim5.service.exception.UnableToPerformActionException;

import java.time.LocalDateTime;

public interface IDebitService {

    Debit save(Debit debit);

    void delete(Long debitId);

    ReportResponse getReportStatistics(Long clientId, ReportType reportType, boolean showAll, LocalDateTime startDate, LocalDateTime endDate) throws EntityNotFoundException, UnableToPerformActionException;
}
