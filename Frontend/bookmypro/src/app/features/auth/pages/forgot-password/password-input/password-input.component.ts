import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-password-input',
  templateUrl: './password-input.component.html',
  styleUrl: './password-input.component.css'
})
export class PasswordInputComponent {

  @Output()
  sendOtpClicked = new EventEmitter<{
    email: string;
    password: string;
  }>();

  form: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {

    this.form = this.fb.group({

      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],

      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8)
        ]
      ]

    });

  }

  sendOtp() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.sendOtpClicked.emit(this.form.value);

  }


}
