import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password-otp-verification',
  templateUrl: './forgot-password-otp-verification.component.html',
  styleUrl: './forgot-password-otp-verification.component.css'
})
export class ForgotPasswordOtpVerificationComponent {

  @Input()
  email = '';

  @Output()
  verifyClicked = new EventEmitter<string>();

  @Output()
  resendClicked = new EventEmitter<void>();

  @Output()
  changeEmailClicked = new EventEmitter<void>();

  form: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {

    this.form = this.fb.group({

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

  get otpArray(): FormArray {
    return this.form.get('otp') as FormArray;
  }

  get otpControls() {
    return this.otpArray.controls;
  }

  moveNext(
    event: Event,
    index: number
  ) {

    const input =
      event.target as HTMLInputElement;

    if (
      input.value &&
      index < 5
    ) {

      const next =
        input.parentElement?.children[index + 1] as HTMLInputElement;

      next?.focus();

    }

  }

  verifyOtp() {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    this.verifyClicked.emit(
      this.otpArray.value.join('')
    );

  }

  resendOtp() {

    this.resendClicked.emit();

  }

  changeEmail() {

    this.changeEmailClicked.emit();

  }


}
