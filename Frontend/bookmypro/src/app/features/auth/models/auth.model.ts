export interface LoginRequest {
  email: string;
  password: string;
  deviceId: string | null;
  deviceName: string;
  browser: string;
  ipAddress: string;
  location: string;
}

export interface LoginResponse {
  email: string;
  accessToken: string;
  refreshToken: string;
  credentialId: string
  tokenType: string;
  message: string;
  deviceId: string;
  roles?: string[];
}

export interface OnboardingRequest {
  firstName: string;
  lastName: string;
  userName: string;
  email: string;
  phoneNumber: string;
  password: string;
}

export interface OnboardingResponse {
  customerId?: string;     // UUID (for customers)
  providerId?: string;     // UUID (for providers)
  credentialId: string;    // UUID
  message: string;
}

export interface VerifyOtpRequest {
  credentialId: string;
  otpCode: string;
}

export interface VerifyOtpResponse {
  message: string;
}

export interface ForgotPasswordRequest {
  email: string;
  password: string;
}

export interface ForgotPasswordOtpVerifiedRequest {
  email: string;
  password: string;
  otp: string;
}

export interface ForgotPasswordResponse {
  message: string;
}

export interface authMeResponse {
  email: string;
  credentialId: string;
  roles?: string[];
  deviceId?: string;

}