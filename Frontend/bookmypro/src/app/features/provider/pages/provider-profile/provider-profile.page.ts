import { Location } from '@angular/common';
import { Component, ViewChild, OnInit, inject } from '@angular/core';
import { ProviderProfileFormFactory } from '../../form-factories/provider-profile.form.factory';
import { ProviderProfileService } from '../../services/provider-profile.service';
import { AppStore } from '../../../../shared/components/store/app.store';
import { AvailabilityComponent } from './components/availability/availability.component';
import { BankDetailsComponent } from './components/bank-details/bank-details.component';
import { PersonalInfoResponse, ProfessionalInfoResponse } from '../../model/provider-profile.model';

@Component({
  selector: 'app-provider-profile',
  templateUrl: './provider-profile.page.html',
  styleUrl: './provider-profile.page.css',
})
export class ProviderProfilePage implements OnInit {
  profileForm = this.formFactory.create();
  activeSection = 'personal';

  @ViewChild('bankDetailsSection')
  bankDetailsSection?: BankDetailsComponent;

  @ViewChild('availabilitySection')
  availabilitySection?: AvailabilityComponent;

  private profileService = inject(ProviderProfileService);
  public appStore = inject(AppStore);
  credentialId = '';

  constructor(
    private formFactory: ProviderProfileFormFactory,
    private location: Location
  ) { }

  ngOnInit(): void {
    const credId = this.appStore.authUser()?.credentialId;
    if (credId) {
      this.credentialId = credId;
      // Prefill email from the active authenticated user
      const emailVal = this.appStore.authUser()?.email || '';
      this.profileForm.controls.personalInfo.patchValue({ email: emailVal });
    }
  }

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

    this.profileService.savePersonalInfo(this.credentialId, personalInfo.getRawValue()).subscribe({
      next: (profile: PersonalInfoResponse) => {
        alert('Personal information saved successfully!');
      },
      error: (err: any) => {
        console.error('Failed to save provider personal info', err);
        alert('Error saving personal information.');
      }
    });
  }

  private saveProfessional(): void {
    const professionalInfo = this.profileForm.controls.professionalInfo;

    if (professionalInfo.invalid) {
      professionalInfo.markAllAsTouched();
      return;
    }

    this.appStore.setLoading(true)

    this.profileService.saveProfessionalInfo(this.credentialId, professionalInfo.getRawValue()).subscribe({
      next: (profile: ProfessionalInfoResponse) => {
        alert('Professional information saved successfully!');
            this.appStore.setLoading(false)

      },
      error: (err: any) => {
        console.error('Failed to save provider professional info', err);
        alert('Error saving professional information.');
            this.appStore.setLoading(false)
      }
    });
  }
}
