import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { BankInfoResponse } from '../../../../model/provider-profile.model';

@Component({
  selector: 'app-bank-details',
  templateUrl: './bank-details.component.html',
  styleUrl: './bank-details.component.css'
})
export class BankDetailsComponent implements OnInit {
  @Input()
  credentialId = '';

  form: FormGroup;

  private fb = inject(FormBuilder);
  private profileService = inject(ProviderProfileService);

  constructor() {
    this.form = this.fb.group({
      accountHolderName: ['', Validators.required],
      bankName: ['', Validators.required],
      accountNumber: ['', Validators.required],
      confirmAccountNumber: ['', Validators.required],
      ifscCode: ['', [Validators.required, Validators.pattern('^[A-Z]{4}0[A-Z0-9]{6}$')]],
      branchName: [''],
      accountType: ['SAVINGS'],
      upiId: [''],
      panNumber: [''],
      gstNumber: [''],
      primary: [true]
    }, {
      validators: this.accountNumberMatchValidator
    });
  }

  ngOnInit(): void {
    if (this.credentialId) {
      this.profileService.getBankDetails(this.credentialId).subscribe({
        next: (res: BankInfoResponse) => {
          if (res && res.bankId) {
            this.form.patchValue({
              accountHolderName: res.accountHolderName,
              bankName: res.bankName,
              accountNumber: res.accountNumber,
              confirmAccountNumber: res.accountNumber,
              ifscCode: res.ifscCode,
              branchName: res.branchName,
              accountType: res.accountType || 'SAVINGS',
              upiId: res.upiId,
              panNumber: res.panNumber,
              gstNumber: res.gstNumber,
              primary: res.isPrimary !== null ? res.isPrimary : true
            });
          }
        },
        error: (err: any) => {
          console.error('Failed to load bank details', err);
        }
      });
    }
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

    const rawVal = this.form.getRawValue();
    const payload = {
      accountHolderName: rawVal.accountHolderName,
      bankName: rawVal.bankName,
      accountNumber: rawVal.accountNumber,
      ifscCode: rawVal.ifscCode,
      branchName: rawVal.branchName,
      accountType: rawVal.accountType,
      upiId: rawVal.upiId,
      panNumber: rawVal.panNumber,
      gstNumber: rawVal.gstNumber,
      isPrimary: rawVal.primary
    };

    if (this.credentialId) {
      this.profileService.saveBankDetails(this.credentialId, payload).subscribe({
        next: (res: BankInfoResponse) => {
          alert('Bank details saved successfully!');
        },
        error: (err: any) => {
          console.error('Failed to save bank details', err);
          alert('Failed to save bank details.');
        }
      });
    }
  }
}
