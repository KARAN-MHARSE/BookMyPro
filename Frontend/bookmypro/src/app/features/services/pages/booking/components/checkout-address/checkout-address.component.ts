import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CheckoutAddress } from '../../booking.component';

@Component({
  selector: 'app-checkout-address',
  templateUrl: './checkout-address.component.html',
  styleUrl: './checkout-address.component.css'
})
export class CheckoutAddressComponent {
  @Input({ required: true }) addresses: CheckoutAddress[] = [];
  @Input({ required: true }) selectedAddressId: string = '';
  @Output() addressSelected = new EventEmitter<string>();

  onAddressSelect(id: string): void {
    this.addressSelected.emit(id);
  }
}
