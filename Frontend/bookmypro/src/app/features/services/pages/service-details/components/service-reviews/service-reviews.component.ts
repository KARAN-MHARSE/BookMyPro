import { Component, Input } from '@angular/core';
import { Review } from '@app/features/services/models/service.model';

@Component({
  selector: 'app-service-reviews',
  templateUrl: './service-reviews.component.html',
  styleUrl: './service-reviews.component.css'
})
export class ServiceReviewsComponent {
  @Input({ required: true })
  reviews: Review[] = [];

  @Input()
  averageRating = 0;

  @Input()
  totalReviews = 0;

  getStars(rating: number): number[] {
    return Array(Math.round(rating)).fill(0);
  }
}
