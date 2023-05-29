import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { CashCreditRequest } from '../model/cash-credit-request';
import { Debit } from 'src/modules/shared/model/debit';
import { OverdraftRequest } from '../model/overdraft-request';

@Injectable({
  providedIn: 'root'
})
export class OverdraftService {

  constructor(private configService: ConfigService, private http: HttpClient) { }


  sendOverdraftRequest(overdraftRequest: OverdraftRequest): Observable<Debit> {
    return this.http.post<Debit>(
      this.configService.SEND_OVERDRAFT_REQUEST_URL,
      overdraftRequest
    );
  }
}