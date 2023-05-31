
export interface ReportRequest {
    clientId?: number,
    reportType: string,
    showAll: boolean,
    startDate: Date,
    endDate: Date;
}

export interface ReportResponse {
    numOfActive: number;
    numOfPending: number;
    numOfRejected: number;
}