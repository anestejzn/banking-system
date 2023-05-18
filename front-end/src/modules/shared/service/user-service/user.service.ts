import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRegistrationRequest } from 'src/modules/auth/model/model/registration_and_verification/user-registration-request';
import { VerifyRequest } from 'src/modules/auth/model/model/registration_and_verification/verify-request';
import { User } from '../../model/user';
import { ConfigService } from '../config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  verify(verifyRequest: VerifyRequest): Observable<boolean> {
    return this.http.put<boolean>(this.configService.ACTIVATE_ACCOUNT_URL, verifyRequest);
  }

}
