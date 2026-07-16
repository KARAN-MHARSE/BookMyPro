import { Component, Input, OnInit, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { PersonalInfoResponse } from '../../../../model/provider-profile.model';
import { AppStore } from '@shared/components/store/app.store';

@Component({
  selector: 'app-personal-information',
  templateUrl: './personal-information.component.html',
  styleUrl: './personal-information.component.css'
})
export class PersonalInformationComponent implements OnInit {
  @Input()
  group!: FormGroup;

  @Input()
  credentialId = '';

  imagePreview: string | ArrayBuffer | null = null;

  genders = ['Male', 'Female', 'Other'];

  languages = ['English', 'Hindi', 'Marathi'];

  private profileService = inject(ProviderProfileService);
  private appStore = inject(AppStore);

  ngOnInit(): void {
    const photo = this.group.get('profilePhoto')?.value;
    if (photo && typeof photo === 'string') {
      this.imagePreview = photo;
    }

    this.group.get('profilePhoto')?.valueChanges.subscribe({
      next: (val) => {
        if (val && typeof val === 'string') {
          this.imagePreview = val;
        }
      }
    });

    if (this.credentialId) {
      this.profileService.getPersonalInfo(this.credentialId).subscribe({
        next: (profile: PersonalInfoResponse) => {
          this.group.patchValue({
            firstName: profile.firstName,
            middleName: profile.middleName,
            lastName: profile.lastName,
            phoneNumber: profile.phoneNumber,
            dob: profile.dob,
            gender: this.formatGender(profile.gender),
            language: profile.language,
            bio: profile.bio
          });
          if (profile.profilePhoto) {
            this.group.patchValue({ profilePhoto: profile.profilePhoto });
            this.imagePreview = profile.profilePhoto;
          }
        },
        error: (err: any) => {
          console.error('Failed to load provider profile personal info', err);
        }
      });
    }
  }

  selectImage(event: Event): void {
    const file = (event.target as HTMLInputElement)
      .files?.[0];

    if (!file) {
      return;
    }

    this.group.patchValue({
      profilePhoto: file
    });

    const reader = new FileReader();

    reader.onload = () => {
      this.imagePreview = reader.result;
      this.appStore.setProfileImage(reader.result as string);
    };

    reader.readAsDataURL(file);

    if (this.credentialId) {
      this.profileService.updateProfilePhoto(this.credentialId, file).subscribe({
        next: (res) => {
          if (res && res.photoUrl) {
            this.group.patchValue({
              profilePhoto: file
            });
          }
        },
        error: (err) => {
          console.error('Failed to upload profile photo', err);
        }
      });
    }
  }

  private formatGender(gender?: string): string {
    if (!gender) return '';
    const val = gender.toLowerCase();
    return val.charAt(0).toUpperCase() + val.slice(1);
  }
}
