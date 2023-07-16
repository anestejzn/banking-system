package com.ftn.sbnz.tim5.service.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportResponse {

    private Long numOfActive;

    private Long numOfPending;

    private Long numOfRejected;

    public ReportResponse(Long numOfActive, Long numOfPending, Long numOfRejected) {
        this.numOfActive = numOfActive;
        this.numOfPending = numOfPending;
        this.numOfRejected = numOfRejected;
    }
}
