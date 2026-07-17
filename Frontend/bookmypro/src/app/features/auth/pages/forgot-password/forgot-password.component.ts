import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ForgotPasswordOtpVerifiedRequest } from '../../models/auth.model';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
  stage = 1;
  email = '';
  password = '';

  constructor(private router: Router, private authService: AuthService) { }

  onSendOtp(data: {
    email: string;
    password: string;
  }) {
    this.email = data.email;
    this.password = data.password;

    this.authService.sendForgotPasswordOtp(data).subscribe({
      next: (response) => {
        console.log('OTP sent successfully:', response);
        this.stage = 2;
      }, error: (error) => {
        console.error('Error sending OTP:', error);
        alert(error.error.message || 'An error occurred while sending OTP. Please try again.');
      }
    });

  }

  onVerifyOtp(otp: string) {
    let payload: ForgotPasswordOtpVerifiedRequest = {
      email: this.email,
      password: this.password,
      otp: otp
    }
    this.authService.verifyOtpAndResetPassword(payload).subscribe({
      next: (response) => {
        console.log('OTP verified successfully:', response);
        this.stage = 3;
      }, error: (error) => {
        console.error('Error verifying OTP:', error);
        alert(error.error.message || 'An error occurred while verifying OTP. Please try again.');
      }
    });

  }

  resendOtp() {
    this.authService.sendForgotPasswordOtp({ email: this.email, password: this.password }).subscribe({
      next: (response) => {
        console.log('OTP sent successfully:', response);
        this.stage = 2;
      }, error: (error) => {
        console.error('Error sending OTP:', error);
        alert(error.error.message || 'An error occurred while sending OTP. Please try again.');
      }
    });
  }

  onChangeEmail() {
    this.stage = 1;
  }

  continueToLogin() {
    this.router.navigate(['/auth/login']);
  }


}
