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

  getClientById(id: number): Observable<Client> {
    return this.http.get<Client>(this.configService.getClientByIdURL(id));
  }

  getAllVerified() {
    return this.http.get<Client[]>(this.configService.ALL_VERIFIED_CLIENTS);
  }

  registerRegularUser(newUser: UserRegistrationRequest, isRetiree: boolean): Observable<Client> {
    return this.http.post<Client>(
      this.configService.getClientRegistrationURL(isRetiree),
      newUser
    );
  }

  accept(id: number): Observable<boolean> {
    console.log(this.configService.getRegistrationAcceptanceURL(id))
    return this.http.put<boolean>(
      this.configService.getRegistrationAcceptanceURL(id),
      null
    );
  }

  reject(id: number): Observable<boolean> {
    return this.http.put<boolean>(
      this.configService.getRegistrationRejectURL(id),
      null
    );
  }

  getAllPendingClients(): Observable<Client[]> {
    return this.http.get<Client[]>(
      this.configService.ALL_PENDING_CLIENTS);
  }
}
