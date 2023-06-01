import { Injectable } from '@angular/core';
import { Employer } from '../../model/employer';
import { Observable } from 'rxjs';
import { ConfigService } from '../config-service/config.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {
  
  constructor(private http: HttpClient, private configService: ConfigService) { }

  getAll(): Observable<Employer[]> {
    return this.http.get<Employer[]>(
      this.configService.ALL_EMPLOYERS
    );
  }

  save(employerRequest): Observable<Employer> {
    console.log(employerRequest);
    return this.http.post<Employer>(
      this.configService.SAVE_EMPLOYERS_URL, employerRequest
    )
  }
}
