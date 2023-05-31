import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction, TransactionRequest } from '../../model/transaction';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from '../config-service/config.service';
import { ReportRequest, ReportResponse } from '../../model/report';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  getFilteredTransactions(accountId: number, parameter: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.configService.getFilteredTransactions(accountId, parameter));
  }

  getCreditCardReport(request: ReportRequest): Observable<ReportResponse> {
    return this.http.post<ReportResponse>(this.configService.CREDIT_CARD_REPORT_URL, request);
  }

  createTransaction(request: TransactionRequest): Observable<Transaction> {
    return this.http.post<Transaction>(this.configService.TRANSACTION_URL, request);
  }

}
