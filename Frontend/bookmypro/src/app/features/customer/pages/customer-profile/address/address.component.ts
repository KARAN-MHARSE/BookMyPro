import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Address } from '@features/customer/models/customer.model';
import { CustomerProfileService } from '@features/customer/services/customer.profile.service';
import { StorageService } from '@core/services/storage.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent {
  @Input() defaultAddress!: Address;
  @Output() defaultAddressEvent = new EventEmitter<any>();
  addresses: Address[] = [];

  constructor(private router: Router, private customerProfileService: CustomerProfileService, private storageService: StorageService) { }

  selectedAddress: any = null;
  showAddressDialog = false;

  openManageAddresses(): void {
    const credentialId = this.storageService.get("credentialId");
    if (!credentialId) {
      alert("You are not authorized, please login first.")
      return;
    }

    this.customerProfileService.getAddresses(credentialId).subscribe({
      next: (res: Address[]) => {
        this.addresses = res;
      },
      error: (err: any) => {
        alert(err?.error?.message || 'Something went wrong');
        console.error(err);
      }
    });

    this.showAddressDialog = true;
  }

  closeDialog(): void {
    this.showAddressDialog = false;
    this.selectedAddress = null;
  }

  addAddress(): void {
    this.selectedAddress = null;
    this.showAddressDialog = true;
  }

  editAddress(address: Address): void {
    if (address) {
      this.router.navigate(['/customer/address'], { state: { addressId: address.addressId, mode: 'edit' } });
    }
    this.showAddressDialog = true;
  }

}