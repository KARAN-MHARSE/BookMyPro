import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINTS } from '../../../core/constants/api-endpoints';
import { environment } from '../../../../environments/environment';
import { CustomerOnboardingRequest, CustomerOnboardingResponse, LoginRequest, LoginResponse, VerifyOtpRequest, VerifyOtpResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${environment.apiBaseUrl}${API_ENDPOINTS.AUTH.LOGIN}`,
      request
    );
  }

  registerCustomer(request:CustomerOnboardingRequest):Observable<CustomerOnboardingResponse>{
     return this.http.post<CustomerOnboardingResponse>(
      `${environment.customerServiceBaseUrl}${API_ENDPOINTS.AUTH.CUSTOMER_REGISTER}`,
      request
    );
  }

  verifyAuthOtp(request:VerifyOtpRequest):Observable<VerifyOtpResponse>{
     return this.http.post<CustomerOnboardingResponse>(
      `${environment.apiBaseUrl}${API_ENDPOINTS.AUTH.VERIFY_OTP}`,
      request
    );
  }
}
