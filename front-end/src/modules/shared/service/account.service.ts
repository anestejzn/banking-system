import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config-service/config.service';
import { Observable } from 'rxjs';
import { Account } from '../model/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  getAccountById(id: number): Observable<Account> {
    return this.http.get<Account>(this.configService.getAccountByIdURL(id));
  }
}
