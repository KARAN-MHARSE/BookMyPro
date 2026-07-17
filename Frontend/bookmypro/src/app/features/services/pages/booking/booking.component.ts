import { Component } from '@angular/core';

export interface CheckoutAddress {
  id: string;
  name: string;
  isDefault: boolean;
  details: string;
}

export interface PaymentMethod {
  id: string;
  name: string;
  details: string;
  isDefault: boolean;
}

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent {
  addresses: CheckoutAddress[] = [
    {
      id: 'home',
      name: 'Home',
      isDefault: true,
      details: '2110 Fillmore St, Apt 4B · San Francisco, CA 94115'
    },
    {
      id: 'office',
      name: 'Office',
      isDefault: false,
      details: '548 Market St, Fl 12 · San Francisco, CA 94104'
    }
  ];

  payments: PaymentMethod[] = [
    {
      id: 'visa',
      name: 'Visa ending in 4242',
      details: 'Expires 08/29',
      isDefault: true
    },
    {
      id: 'apple',
      name: 'Apple Pay',
      details: 'Touch ID',
      isDefault: false
    },
    {
      id: 'credit',
      name: 'Wallet credit',
      details: '$45.00 available',
      isDefault: false
    }
  ];

  selectedAddressId = 'home';
  selectedPaymentId = 'visa';

  bookingDate = 'Sat, Feb 8, 2026';
  bookingTime = '10:00 AM';
  homeSize = '2 BR · 1 bathroom';
  bookingFrequency = 'One-time';
  notes = '';

  timeOptions = ['10:00 AM', '11:00 AM', '1:00 PM'];
  homeSizeOptions = ['1 BR · 1 bathroom', '2 BR · 1 bathroom', '3 BR · 2 bathrooms', '4 BR · 3 bathrooms'];
  frequencyOptions = ['One-time', 'Weekly', 'Monthly'];

  couponCode = 'FEB20OFF';
  couponApplied = true;

  // Prices
  servicePrice = 599;
  visitCharge = 49;
  taxRate = 0.18; // 18% GST

  // Booking success flag
  bookingConfirmed = false;

  get discount(): number {
    return this.couponApplied ? 120 : 0;
  }

  get taxableAmount(): number {
    const val = this.servicePrice + this.visitCharge - this.discount;
    return val > 0 ? val : 0;
  }

  get taxes(): number {
    return Math.round(this.taxableAmount * this.taxRate);
  }

  get total(): number {
    return this.taxableAmount + this.taxes;
  }

  applyCoupon(): void {
    if (this.couponCode.trim().toUpperCase() === 'FEB20OFF') {
      this.couponApplied = true;
    }
  }

  removeCoupon(): void {
    this.couponApplied = false;
  }

  selectAddress(id: string): void {
    this.selectedAddressId = id;
  }

  selectPayment(id: string): void {
    this.selectedPaymentId = id;
  }

  confirmBooking(): void {
    this.bookingConfirmed = true;
  }
}
