import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({
      withCredentials: true,
      setHeaders: {
        "Authorization": `Bearer ${sessionStorage.getItem("token") || ''}`,
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Origin": "http://localhost:4200"
      }
    });
    return next.handle(request);
  }
}

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     const accessToken = localStorage.getItem("token");
//     return accessToken? next.handle(req.clone(
//       {headers: req.headers.set('Authorization', 'Bearer ' + accessToken)}))
//       : next.handle(req)
//   }
// }
