import { Component, Input } from '@angular/core';
import { Review } from '../../provider-profile.component';

@Component({
  selector: 'app-provider-reviews',
  templateUrl: './provider-reviews.component.html',
  styleUrl: './provider-reviews.component.css'
})
export class ProviderReviewsComponent {
  @Input({ required: true }) reviews!: Review[];

  getStars(rating: number): string {
    const fullStars = Math.floor(rating);
    const hasHalf = rating % 1 >= 0.5;
    return '★'.repeat(fullStars) + (hasHalf ? '★' : '') + '☆'.repeat(5 - fullStars - (hasHalf ? 1 : 0));
  }
}
