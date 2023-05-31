package com.ftn.sbnz.tim5.service.dto.request;

import com.ftn.sbnz.tim5.model.enums.ReportType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReportRequest {

    private Long clientId;

    @NotNull(message = "Report type cannot be empty")
    private ReportType reportType;

    private boolean showAll;

    @NotNull(message = "Start date cannot be empty")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be empty")
    private LocalDateTime endDate;

    public ReportRequest(Long clientId,
                         ReportType reportType,
                         boolean showAll,
                         LocalDateTime startDate,
                         LocalDateTime endDate
    ) {
        this.clientId = clientId;
        this.reportType = reportType;
        this.showAll = showAll;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
