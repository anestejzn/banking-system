import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/modules/shared/model/user';
import { LoginRequest } from '../../model/model/login/login-request';
import { LoginResponse } from '../../model/model/login/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 
  public currentUser$: BehaviorSubject<User>;

  constructor(private configService: ConfigService, private http: HttpClient) {
    this.currentUser$ = new BehaviorSubject<User>(null);
   }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      this.configService.getLoginUrl(),
      loginRequest
    );
  }

  setSessionStorage(loginResponse: LoginResponse): void {
    sessionStorage.setItem('token', loginResponse.token);
    sessionStorage.setItem('user', JSON.stringify(loginResponse.user));
    sessionStorage.setItem('email', loginResponse.user.email);
    this.currentUser$.next(loginResponse.user);
  }

  logOut(): Observable<null> {
    // this.webSocketService.disconnect();
    this.currentUser$.next(null);
    return this.http.post<null>(
      this.configService.getLogoutUrl(),
      null
    );
  }


  getSubjectCurrentUser(): BehaviorSubject<User> {
    const user = sessionStorage.getItem('user');
    if (user !== null && user !== undefined) {
      const parsedUser: User = JSON.parse(user);
      this.currentUser$.next(parsedUser);
    } else {
      this.currentUser$.next(null);
    }

    return this.currentUser$;
  }
}
