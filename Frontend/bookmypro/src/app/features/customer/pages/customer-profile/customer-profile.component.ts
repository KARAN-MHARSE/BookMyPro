import { Component, inject, OnInit, effect } from '@angular/core';
import { Location } from '@angular/common';
import { filter } from 'rxjs';
import { Address, CustomerProfileResponse } from '@features/customer/models/customer.model';
import { ProfileFormFactory } from '@features/customer/form-factories/profile.form.factory';
import { CustomerProfileService } from '@features/customer/services/customer.profile.service';
import { LookUpStore } from '@features/customer/store/look-up.store';
import { StorageService } from '@core/services/storage.service';
import { AppStore } from '@core/store/app.store';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrl: './customer-profile.component.css'
})
export class CustomerProfileComponent implements OnInit {
  profileForm = this.factory.create();
  defaultAddressId = "";
  defaultAddress!: Address;
  profileImage = ''
  private credentialId: string | null = '';

  private storageService = inject(StorageService)
  private location = inject(Location)
  private lookUpStore = inject(LookUpStore)
  private appStore = inject(AppStore)


  constructor(private factory: ProfileFormFactory, private customerProfileService: CustomerProfileService) {
    effect(() => {
      const isAuthorized = this.appStore.isAuthorized();
      console.log(this.appStore.authUser())
      if (isAuthorized) {
        this.loadProfile();
      }
    }, { allowSignalWrites: true });
  }

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    this.credentialId = this.storageService.get("credentialId") ?? null;

    if (this.credentialId) {
      this.customerProfileService.getProfile(this.credentialId)
        .pipe(filter((res: any) => res))
        .subscribe((res: CustomerProfileResponse) => {
          const personalInfoForm = this.profileForm.controls.personalInfo;
          personalInfoForm.patchValue(res?.personalInfo);
          this.defaultAddress = res?.defaultAddress;
          const photo = res?.personalInfo?.profilePhoto;
          this.profileImage = photo ? `data:image/jpeg;base64,${photo}` : '';

          this.lookUpStore.setLookups(res.lookups);
        });
    }
  }

  onDefaultAddressChange(address: any) {
    this.defaultAddressId = address ? address.addressId : "";
  }

  saveProfile(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      alert('Enter all data');
      // return;
    }

    const credentialId = this.storageService.get("credentialId");
    const personalInfoForm = this.profileForm.controls.personalInfo;
    const personalInfo = personalInfoForm.value;
    console.log('Personal Info:', personalInfo);

    const formData = new FormData();

    formData.append('credentialId', credentialId || '');
    formData.append('firstName', personalInfo.firstName || '');
    formData.append('middleName', personalInfo.middleName || '');
    formData.append('lastName', personalInfo.lastName || '');
    formData.append('dob', personalInfo.dob || '');
    formData.append('gender', personalInfo.gender?.toUpperCase() || '');
    formData.append('language', personalInfo.language || '');
    formData.append('occupation', personalInfo.occupation || '');
    formData.append('bio', personalInfo.bio || '');
    // formData.append('defaultAddressId', this.defaultAddressId || '');

    if (personalInfo?.profilePhoto) {
      formData.append('profilePhoto', personalInfo.profilePhoto);
    }

    this.customerProfileService.saveProfile(formData).subscribe({
      next: response => {
        console.log(response);
      },
      error: error => {
        console.error(error);
      }
    });
  }

  cancel() {
    this.location.back()
  }

}
