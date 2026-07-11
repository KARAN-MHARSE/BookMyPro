import { Injectable, inject } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthTokenService } from '../services/auth-token.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private authTokenService = inject(AuthTokenService);

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const token = this.authTokenService.getAccessToken();

    if (!token) {
      return next.handle(request);
    }

    return next.handle(
      request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      })
    );
  }
}
