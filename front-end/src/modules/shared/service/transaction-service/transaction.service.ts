import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction, TransactionRequest } from '../../model/transaction';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from '../config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  getFilteredTransactions(accountId: number, parameter: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.configService.getFilteredTransactions(accountId, parameter));
  }

  createTransaction(request: TransactionRequest): Observable<Transaction> {
    return this.http.post<Transaction>(this.configService.TRANSACTION_URL, request);
  }

}
