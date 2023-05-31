package com.ftn.sbnz.tim5.service.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportResponse {

    private int numOfActive;

    private int numOfPending;

    private int numOfRejected;

    public ReportResponse(int numOfActive, int numOfPending, int numOfRejected) {
        this.numOfActive = numOfActive;
        this.numOfPending = numOfPending;
        this.numOfRejected = numOfRejected;
    }
}
