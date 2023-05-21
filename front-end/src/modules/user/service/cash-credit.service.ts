import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { CashCreditRequest } from '../model/cash-credit-request';
import { Debit } from 'src/modules/shared/model/debit';

@Injectable({
  providedIn: 'root'
})
export class CashCreditService {

  constructor(private configService: ConfigService, private http: HttpClient) { }


  sendCashCreditRequest(cashCreditRequest: CashCreditRequest): Observable<Debit> {
    return this.http.post<Debit>(
      this.configService.SEND_CASH_CREDIT_REQUEST_URL,
      cashCreditRequest
    );
  }
}
