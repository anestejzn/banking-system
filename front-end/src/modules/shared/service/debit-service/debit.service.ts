import { Injectable } from '@angular/core';
import { ConfigService } from '../config-service/config.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DebitService {

  constructor(private configService: ConfigService, private http: HttpClient) { }

  cancelDebitRequest(debitId: number){
    return this.http.delete(this.configService.getCancelDebitUrl(debitId));
  }
}
