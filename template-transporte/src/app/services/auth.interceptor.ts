import { Injectable } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

  constructor() { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('accessToken');
    console.log("TOKEN!!!: " + token)
    
    if (token!=null) {
      request = request.clone({
        setHeaders : {Authorization: `Bearer ${token}` }
      })
    }
    return next.handle(request);
  }
}
