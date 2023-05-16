import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() { }

  API_URL = environment.apiUrl;
  ///////////////////AUTH///////////////////
  AUTH_URL = `${this.API_URL}/auth`;
  LOGIN_URL = `${this.AUTH_URL}/login`;
  LOGOUT_URL = `${this.AUTH_URL}/logout`;

  getLoginUrl(): string {
    return this.LOGIN_URL;
  }

  getLogoutUrl(): string {
    return this.LOGOUT_URL;
  }


  //////////////////USERS////////////////
  USERS_URL = `${this.API_URL}/users`;
  CREATE_USER_URL = `${this.USERS_URL}/register`;
  ACTIVATE_ACCOUNT_URL = `${this.USERS_URL}/activate-account`;

  /////////////////VERIFY/////////////////
  VERIFY_URL = `${this.API_URL}/verify`;
  SEND_CODE_AGAIN_URL = `${this.VERIFY_URL}/send-code-again`;

}
