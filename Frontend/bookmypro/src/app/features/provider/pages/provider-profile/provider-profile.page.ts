import { Location } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { ProviderProfileFormFactory } from '../../form-factories/provider-profile.form.factory';
import { AvailabilityComponent } from './components/availability/availability.component';
import { BankDetailsComponent } from './components/bank-details/bank-details.component';

@Component({
  selector: 'app-provider-profile',
  templateUrl: './provider-profile.page.html',
  styleUrl: './provider-profile.page.css',
})
export class ProviderProfilePage {
  profileForm = this.formFactory.create();
  activeSection = 'personal';

  @ViewChild('availabilitySection')
  availabilitySection?: AvailabilityComponent;

  @ViewChild('bankDetailsSection')
  bankDetailsSection?: BankDetailsComponent;

  constructor(
    private formFactory: ProviderProfileFormFactory,
    private location: Location
  ) {}

  changeSection(section: string): void {
    this.activeSection = section;
  }

  saveCurrentSection(): void {
    switch (this.activeSection) {
      case 'personal':
        this.savePersonal();
        break;
      case 'professional':
        this.saveProfessional();
        break;
      case 'availability':
        this.availabilitySection?.saveAvailability();
        break;
      case 'bank':
        this.bankDetailsSection?.save();
        break;
      default:
        console.log(`Save ${this.activeSection} - API not implemented yet`);
    }
  }

  cancel(): void {
    this.location.back();
  }

  private savePersonal(): void {
    const personalInfo = this.profileForm.controls.personalInfo;

    if (personalInfo.invalid) {
      personalInfo.markAllAsTouched();
      return;
    }

    console.log('Save personal info:', personalInfo.getRawValue());
    // call API here
  }

  private saveProfessional(): void {
    const professionalInfo = this.profileForm.controls.professionalInfo;

    if (professionalInfo.invalid) {
      professionalInfo.markAllAsTouched();
      return;
    }

    console.log('Save professional info:', professionalInfo.getRawValue());
    // call API here
  }
}
