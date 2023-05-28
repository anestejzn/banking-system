import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from '../config-service/config.service';
import { Observable } from 'rxjs';
import { CreditCardRequest } from '../../model/credit-card-request';

@Injectable({
  providedIn: 'root'
})
export class CreditCardService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  addNewCreditCard(request: CreditCardRequest): Observable<boolean> {
    return this.http.post<boolean>(this.configService.NEW_CREDIT_CARD_URL, request);
  }

}
