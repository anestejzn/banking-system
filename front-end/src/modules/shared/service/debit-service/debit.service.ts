import { Injectable } from '@angular/core';
import { ConfigService } from '../config-service/config.service';
import { HttpClient } from '@angular/common/http';
import { ReportRequest, ReportResponse } from '../../model/report';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DebitService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  getDebitReport(request: ReportRequest): Observable<ReportResponse> {
    return this.http.post<ReportResponse>(this.configService.DEBIT_REPORT_URL, request);
  }

  cancelDebitRequest(debitId: number){
    return this.http.delete(this.configService.getCancelDebitUrl(debitId));
  }
}
