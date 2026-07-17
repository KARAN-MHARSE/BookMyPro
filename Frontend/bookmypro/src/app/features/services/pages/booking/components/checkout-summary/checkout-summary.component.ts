import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-checkout-summary',
  templateUrl: './checkout-summary.component.html',
  styleUrl: './checkout-summary.component.css'
})
export class CheckoutSummaryComponent {
  @Input({ required: true }) homeSize!: string;
  @Input({ required: true }) bookingDate!: string;
  @Input({ required: true }) bookingTime!: string;
  @Input({ required: true }) couponCode!: string;
  @Input({ required: true }) couponApplied!: boolean;
  @Input({ required: true }) servicePrice!: number;
  @Input({ required: true }) visitCharge!: number;
  @Input({ required: true }) discount!: number;
  @Input({ required: true }) taxes!: number;
  @Input({ required: true }) total!: number;

  @Output() couponCodeChange = new EventEmitter<string>();
  @Output() couponApply = new EventEmitter<void>();
  @Output() couponRemove = new EventEmitter<void>();
  @Output() confirm = new EventEmitter<void>();

  onCouponCodeInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.couponCodeChange.emit(value);
  }

  onCouponApply(): void {
    this.couponApply.emit();
  }

  onCouponRemove(): void {
    this.couponRemove.emit();
  }

  onConfirm(): void {
    this.confirm.emit();
  }
}
