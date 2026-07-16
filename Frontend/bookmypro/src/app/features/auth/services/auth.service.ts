import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_ENDPOINTS } from '../../../core/constants/api-endpoints';
import { environment } from '../../../../environments/environment';
import { ForgotPasswordOtpVerifiedRequest, ForgotPasswordRequest, ForgotPasswordResponse, LoginRequest, LoginResponse, VerifyOtpRequest, VerifyOtpResponse, OnboardingRequest, OnboardingResponse, authMeResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${environment.identityServiceBaseUrl}${API_ENDPOINTS.AUTH.LOGIN}`,
      request
    );
  }

  registerCustomer(request: OnboardingRequest): Observable<OnboardingResponse> {
    return this.http.post<OnboardingResponse>(
      `${environment.customerServiceBaseUrl}${API_ENDPOINTS.AUTH.CUSTOMER_REGISTER}`,
      request
    );
  }

  registerProvider(request: OnboardingRequest): Observable<OnboardingResponse> {
    return this.http.post<OnboardingResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.AUTH.PROVIDER_REGISTER}`,
      request
    );
  }

  verifyAuthOtp(request: VerifyOtpRequest): Observable<VerifyOtpResponse> {
    return this.http.post<VerifyOtpResponse>(
      `${environment.identityServiceBaseUrl}${API_ENDPOINTS.AUTH.VERIFY_OTP}`,
      request
    );
  }

  sendForgotPasswordOtp(
    request: ForgotPasswordRequest
  ): Observable<ForgotPasswordResponse> {
    return this.http.post<ForgotPasswordResponse>(
      `${environment.identityServiceBaseUrl}${API_ENDPOINTS.AUTH.FORGOT_PASSWORD_SEND_OTP}`,
      request
    );
  }

  verifyOtpAndResetPassword(
    request: ForgotPasswordOtpVerifiedRequest
  ): Observable<ForgotPasswordResponse> {
    return this.http.post<ForgotPasswordResponse>(
      `${environment.identityServiceBaseUrl}${API_ENDPOINTS.AUTH.FORGOT_PASSWORD_VALIDATE_OTP}`,
      request
    );
  }

  authMe(
    deviceId: string | null
  ): Observable<authMeResponse> {
    const headers: { [key: string]: string } = {};
    if (deviceId) {
      headers['deviceId'] = deviceId;
    }
    return this.http.get<authMeResponse>(
      `${environment.identityServiceBaseUrl}${API_ENDPOINTS.AUTH.AUTH_ME}`,
      { headers }
    );
  }

}
