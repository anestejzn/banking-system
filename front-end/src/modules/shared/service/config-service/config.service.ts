import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() { }

  //////////////////REPORTS/////////////////
  CASH_CREDIT_REPORT = "CASH_CREDIT_REPORT";
  CREDIT_CARD_REPORT = "CREDIT_CARD_REPORT";
  OVERDRAFT_REPORT = "OVERDRAFT_REPORT";


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

  /////////////////ACCOUNT TYPES/////////////////
  ACCOUNT_TYPES_URL = `${this.API_URL}/account-types`;
  ALL_ACCOUNT_TYPES = `${this.ACCOUNT_TYPES_URL}/all-types`;

  /////////////////EMPLOYERS/////////////////
  EMPLOYERS_URL = `${this.API_URL}/employers`;
  ALL_EMPLOYERS = `${this.EMPLOYERS_URL}/all-employers`;
  SAVE_EMPLOYERS_URL = `${this.API_URL}/employers/save`;

  ////////////////CLIENT////////////////////////
  CLIENTS_URL = `${this.API_URL}/clients`;
  ALL_PENDING_CLIENTS = `${this.CLIENTS_URL}/all-pending-clients`
  ALL_VERIFIED_CLIENTS  = `${this.API_URL}/clients/all-verified`
  
  getClientRegistrationURL(isRetiree: boolean): string {
    return isRetiree ? `${this.CLIENTS_URL}/register-retired-client` : `${this.CLIENTS_URL}/register-employed-client`;
  }

  getRegistrationAcceptanceURL(id: number): string {
    return`${this.CLIENTS_URL}/accept-registration-request/${id}`;
  }

  getRegistrationRejectURL(id: number): string {
    return `${this.CLIENTS_URL}/reject-registration-request/${id}`;
  }

  getClientByIdURL(id: number): string {
    return `${this.CLIENTS_URL}/${id}`;
  }

  //////////////////ACCOUNTS//////////
  ACCOUNTS_URL = `${this.API_URL}/accounts`;

  getAccountByIdURL(id: number): string {
    return `${this.ACCOUNTS_URL}/${id}`;
  }

  //////////////////CASH_CREDIT//////
  CASH_CREDIT_URL = `${this.API_URL}/cash-credit`;
  SEND_CASH_CREDIT_REQUEST_URL = `${this.CASH_CREDIT_URL}/send-cash-credit-request`;

  ///////////////DEBIT//////////
  DEBIT_URL = `${this.API_URL}/debit`;
  DEBIT_REPORT_URL = `${this.API_URL}/debit/report`;

  getCancelDebitUrl(debitId: number){
    return `${this.DEBIT_URL}/${debitId}`;
  }

  getAcceptDebitUrl(debitId: number){
    return `${this.DEBIT_URL}/accept/${debitId}`;
  }

  ////////////TRANSACTION///////
  TRANSACTION_URL = `${this.API_URL}/transactions`;
  CREDIT_CARD_REPORT_URL = `${this.API_URL}/transactions/report`

  getFilteredTransactions(accountId, parameter): string {

    return `${this.TRANSACTION_URL}/${accountId}/${parameter}`;
  }

  ///////////CREDIT CARD///////
  CREDIT_CARD_URL = `${this.API_URL}/credit-cards`;
  NEW_CREDIT_CARD_URL = `${this.API_URL}/credit-cards/create`;

  /////////OVERDRAFT/////
  OVERDRAFT_URL = `${this.API_URL}/overdraft`;
  SEND_OVERDRAFT_REQUEST_URL = `${this.OVERDRAFT_URL}/send-overdraft-request`;

}
