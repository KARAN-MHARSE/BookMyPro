import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-checkout-booking-details',
  templateUrl: './checkout-booking-details.component.html',
  styleUrl: './checkout-booking-details.component.css'
})
export class CheckoutBookingDetailsComponent {
  @Input({ required: true }) bookingDate!: string;
  @Input({ required: true }) bookingTime!: string;
  @Input({ required: true }) homeSize!: string;
  @Input({ required: true }) bookingFrequency!: string;
  @Input({ required: true }) notes!: string;

  @Input({ required: true }) timeOptions: string[] = [];
  @Input({ required: true }) homeSizeOptions: string[] = [];
  @Input({ required: true }) frequencyOptions: string[] = [];

  @Output() bookingDateChange = new EventEmitter<string>();
  @Output() bookingTimeChange = new EventEmitter<string>();
  @Output() homeSizeChange = new EventEmitter<string>();
  @Output() bookingFrequencyChange = new EventEmitter<string>();
  @Output() notesChange = new EventEmitter<string>();

  onDateInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.bookingDateChange.emit(value);
  }

  onTimeSelect(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.bookingTimeChange.emit(value);
  }

  onHomeSizeSelect(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.homeSizeChange.emit(value);
  }

  onFrequencySelect(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.bookingFrequencyChange.emit(value);
  }

  onNotesInput(event: Event): void {
    const value = (event.target as HTMLTextAreaElement).value;
    this.notesChange.emit(value);
  }
}
