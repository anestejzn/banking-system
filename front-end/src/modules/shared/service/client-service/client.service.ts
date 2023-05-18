import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserRegistrationRequest } from 'src/modules/auth/model/model/registration_and_verification/user-registration-request';
import { ConfigService } from '../config-service/config.service';
import { Observable } from 'rxjs';
import { Client } from '../../model/user';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  registerRegularUser(newUser: UserRegistrationRequest, isRetiree: boolean): Observable<Client> {
    return this.http.post<Client>(
      this.configService.getClientRegistrationURL(isRetiree),
      newUser
    );
  }
}
