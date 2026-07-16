import { Component, Input, OnInit, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { ProfessionalInfoResponse } from '../../../../model/provider-profile.model';

@Component({
  selector: 'app-professional-information',
  templateUrl: './professional-information.component.html',
  styleUrl: './professional-information.component.css'
})
export class ProfessionalInformationComponent implements OnInit {
  @Input()
  group!: FormGroup;

  @Input()
  credentialId = '';

  providerTypes = [
    'Electrician',
    'Plumber',
    'Carpenter',
    'Painter',
    'Cleaner',
    'Mechanic',
    'AC Technician',
    'Appliance Repair',
    'Salon',
    'Tutor',
    'Fitness Trainer'
  ];

  private profileService = inject(ProviderProfileService);

  ngOnInit(): void {
    if (this.credentialId) {
      this.profileService.getProfessionalInfo(this.credentialId).subscribe({
        next: (profile: ProfessionalInfoResponse) => {
          this.group.patchValue({
            professionalTitle: profile.professionalTitle,
            providerType: profile.providerType,
            experience: profile.experience,
            businessName: profile.businessName,
            workingSince: profile.workingSince,
            gstNumber: profile.gstNumber,
            panNumber: profile.panNumber,
            website: profile.website,
            portfolio: profile.portfolio,
            professionalSummary: profile.professionalSummary
          });
        },
        error: (err: any) => {
          console.error('Failed to load professional info', err);
        }
      });
    }
  }
}
