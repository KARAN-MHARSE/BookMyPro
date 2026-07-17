import { Component, Input, Output, EventEmitter } from '@angular/core';
import { PaymentMethod } from '../../booking.component';

@Component({
  selector: 'app-checkout-payment',
  templateUrl: './checkout-payment.component.html',
  styleUrl: './checkout-payment.component.css'
})
export class CheckoutPaymentComponent {
  @Input({ required: true }) payments: PaymentMethod[] = [];
  @Input({ required: true }) selectedPaymentId: string = '';
  @Output() paymentSelected = new EventEmitter<string>();

  onPaymentSelect(id: string): void {
    this.paymentSelected.emit(id);
  }
}
