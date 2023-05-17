import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from '../config-service/config.service';
import { AccountType } from '../../model/account-type';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountTypeService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  getAll(): Observable<AccountType[]> {
    return this.http.get<AccountType[]>(
      this.configService.ALL_ACCOUNT_TYPES
    );
  }

}
