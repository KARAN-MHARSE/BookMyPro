import { Component } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { OnboardingRequest, VerifyOtpRequest } from '../../models/auth.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  signupForm: FormGroup;
  otpForm: FormGroup;

  showOtp = false;
  isProfessional = false;

  private credentialId = '';
  private customerId = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {

    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      phoneNumber: [''],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8)
        ]
      ],
      terms: [
        false,
        Validators.requiredTrue
      ]
    });

    this.otpForm = this.fb.group({
      otp: this.fb.array(
        Array.from(
          { length: 6 },
          () =>
            this.fb.control('', [
              Validators.required,
              Validators.pattern(/^[0-9]$/)
            ])
        )
      )
    });

  }

  get f() {
    return this.signupForm.controls;
  }

  get otpArray(): FormArray {
    return this.otpForm.get('otp') as FormArray;
  }

  get otpControls() {
    return this.otpArray.controls;
  }

  selectRole(value: boolean) {
    this.isProfessional = value;
  }

  moveNext(event: Event, index: number) {
    const input = event.target as HTMLInputElement;
    if (input.value.length === 1 && index < 5) {
      const next = input.parentElement?.children[index + 1] as HTMLInputElement;
      next?.focus();
    }
  }

  createAccount() {
    if (this.signupForm.invalid) {
      this.signupForm.markAllAsTouched();
      return;
    }

    const request = this.signupForm.value as OnboardingRequest;

    if (!this.isProfessional) {
      this.authService
        .registerCustomer(request)
        .subscribe({
          next: (response) => {
            this.credentialId = response.credentialId;
            this.customerId = response.customerId || '';
            this.showOtp = true;
          },
          error: (error) => {
            console.error(error);
          }
        });
    } else {
      this.authService
        .registerProvider(request)
        .subscribe({
          next: (response) => {
            this.credentialId = response.credentialId;
            this.showOtp = true;
          },
          error: (error) => {
            console.error(error);
          }
        });
    }
  }

  verifyOtp() {
    if (this.otpForm.invalid) {
      this.otpForm.markAllAsTouched();
      return;
    }

    const otp = this.otpArray.value.join('');

    console.log('OTP :', otp);
    const request: VerifyOtpRequest = {
      credentialId: this.credentialId,
      otpCode: otp
    }
    this.authService.verifyAuthOtp(request)
      .subscribe({
        next: (response) => {
          this.showOtp = true;
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

  closeOtp() {
    this.showOtp = false;
    this.otpForm.reset();
    this.otpArray.controls.forEach(control => control.setValue(''));
  }

}