import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-bank-details',
  templateUrl: './bank-details.component.html',
  styleUrl: './bank-details.component.css'
})
export class BankDetailsComponent {

  form: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {

    this.form = this.fb.group({

      accountHolderName: [
        'Karan Mharse',
        Validators.required
      ],

      bankName: [
        'State Bank of India',
        Validators.required
      ],

      accountNumber: [
        '30987654321',
        Validators.required
      ],

      confirmAccountNumber: [
        '30987654321',
        Validators.required
      ],

      ifscCode: [
        'SBIN0001234',
        [Validators.required, Validators.pattern('^[A-Z]{4}0[A-Z0-9]{6}$')]
      ],

      branchName: ['Deccan Gymkhana'],

      accountType: [
        'SAVINGS'
      ],

      upiId: ['karan@oksbi'],

      panNumber: ['ABCDE1234F'],

      gstNumber: [''],

      primary: [true]

    }, {
      validators: this.accountNumberMatchValidator
    });

  }

  accountNumberMatchValidator(g: FormGroup) {
    const accNum = g.get('accountNumber')?.value;
    const confirmAccNum = g.get('confirmAccountNumber')?.value;
    return accNum === confirmAccNum ? null : { mismatch: true };
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    console.log('Saved bank details payload:', this.form.getRawValue());
  }
}

