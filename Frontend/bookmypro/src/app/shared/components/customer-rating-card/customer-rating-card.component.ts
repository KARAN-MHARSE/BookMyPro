import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-customer-rating-card',
  templateUrl: './customer-rating-card.component.html',
  styleUrl: './customer-rating-card.component.css'
})
export class CustomerRatingCardComponent {

  @Input() rating = 5;

  @Input({ required: true })
  customerName!: string;

  @Input({ required: true })
  location!: string;

  @Input({ required: true })
  review!: string;

  @Input() avatar = 'SR';

  @Input()
  avatarGradient =
    'linear-gradient(135deg,#4F46E5,#2563EB)';

  get stars(): string {
    const full = Math.round(this.rating);
    return '★'.repeat(full) + '☆'.repeat(5 - full);
  }
}
