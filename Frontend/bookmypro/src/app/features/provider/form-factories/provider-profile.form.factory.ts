import { Injectable } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class ProviderProfileFormFactory {
  constructor(private fb: FormBuilder) {}

  create() {
    return this.fb.group({
      personalInfo: this.fb.group({
        profilePhoto: this.fb.control<string | null>(null),
        firstName: ['', Validators.required],
        middleName: [''],
        lastName: ['', Validators.required],
        dob: ['', Validators.required],
        gender: ['', Validators.required],
        language: ['', Validators.required],
        phoneNumber: ['', Validators.required],
        email: [{ value: '', disabled: true }],
        bio: [''],
      }),
      professionalInfo: this.fb.group({
        professionalTitle: ['', Validators.required],
        providerType: ['', Validators.required],
        experience: ['', Validators.required],
        businessName: [''],
        workingSince: [''],
        gstNumber: [''],
        panNumber: [''],
        website: [''],
        portfolio: [''],
        professionalSummary: [''],
      }),
    });
  }
}
