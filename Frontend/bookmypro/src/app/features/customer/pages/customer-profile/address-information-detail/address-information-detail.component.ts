import { Component, inject, Input, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Address, CustomerAddressRequest } from '@features/customer/models/customer.model';
import { CustomerProfileService } from '@features/customer/services/customer.profile.service';
import { StorageService } from '@core/services/storage.service';

@Component({
  selector: 'app-address-detail-information',
  templateUrl: './address-information-detail.component.html',
  styleUrl: './address-information-detail.component.css'
})
export class AddressInformationDetailComponent implements OnInit {
  @Input({ required: true })
  group!: FormGroup;
  addressId = '';

  private storageService = inject(StorageService);
  private customerProfileService = inject(CustomerProfileService);
  private location = inject(Location)

  countries = [
    'United States',
    'Canada',
    'United Kingdom',
    'India',
    'Australia'
  ];

  form!: any;

  addressTypes = [
    {
      value: 'HOME',
      label: 'Home',
      icon: '🏠'
    },
    {
      value: 'OFFICE',
      label: 'Work',
      icon: '🏢'
    },
    {
      value: 'OTHER',
      label: 'Other',
      icon: '📍'
    }
  ];

  constructor(private fb: FormBuilder, private route: ActivatedRoute) {
    this.group = this.fb.group({

      addressType: [
        'HOME',
        Validators.required
      ],

      addressName: [
        'Home',
        Validators.required
      ],

      landmark: [
        'Opposite Blue Bottle Coffee'
      ],

      addressLine1: [
        '2410 South Lamar Blvd',
        Validators.required
      ],

      addressLine2: [
        'Apt 302'
      ],

      city: [
        'Austin',
        Validators.required
      ],

      district: [
        'Travis'
      ],

      state: [
        'Texas',
        Validators.required
      ],

      country: [
        'United States',
        Validators.required
      ],

      postalCode: [
        '78704',
        Validators.required
      ],

      defaultAddress: [
        true
      ],

      latitude: [
        '30.2519'
      ],

      longitude: [
        '-97.7684'
      ]

    })
  }

  ngOnInit(): void {
    console.log(history.state)
    this.addressId = history.state?.addressId;

    if(this.addressId){
      this.customerProfileService.getAddressById(this.addressId).subscribe({
        next: (res: Address) => {
          this.group.patchValue(res);
        }
      });
    }
  }

  saveAddressEvent() {
    let credentialId = this.storageService.get("credentialId")
    if (!credentialId) {
      alert("You are not authorized user. Please login to continue.")
      return;
    }
    let address: Address = this.group.value;
    address.addressId = this.addressId;
    const request: CustomerAddressRequest = {
      credentialId: credentialId,
      address: address
    }

    this.customerProfileService.saveOrUpdateCustomeraAddress(request).subscribe({
      next: (response: any) => {
        console.log('Address saved:', response);
      },
      error: (error: any) => {
        debugger
        alert(error.error.message || 'An error occurred while saving the address.');
        console.error('Error saving address:', error);
      }
    });

  }
  useCurrentLocation() { }

  cancel(){
    this.location.back()
  }
}
