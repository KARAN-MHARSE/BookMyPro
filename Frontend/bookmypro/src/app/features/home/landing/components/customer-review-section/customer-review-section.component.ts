import { Component } from '@angular/core';

@Component({
  selector: 'app-customer-review-section',
  templateUrl: './customer-review-section.component.html',
  styleUrl: './customer-review-section.component.css'
})
export class CustomerReviewSectionComponent {

  reviews = [
    {
      customerName: 'Sarah R.',
      location: 'Brooklyn, NY',
      avatar: 'SR',
      avatarGradient: 'linear-gradient(135deg,#4F46E5,#2563EB)',
      rating: 5,
      review: 'Booked a deep clean at 9 PM and my apartment was spotless by lunch the next day. The professional was friendly and efficient.'
    },
    {
      customerName: 'Daniel M.',
      location: 'San Francisco, CA',
      avatar: 'DM',
      avatarGradient: 'linear-gradient(135deg,#F472B6,#F59E0B)',
      rating: 5,
      review: 'Finally a service where the price you see is exactly what you pay. The electrician arrived on time and fixed everything quickly.'
    },
    {
      customerName: 'Aisha K.',
      location: 'Austin, TX',
      avatar: 'AK',
      avatarGradient: 'linear-gradient(135deg,#34D399,#3B82F6)',
      rating: 4,
      review: 'Loved being able to reschedule with just a few taps. Customer support was responsive and the booking experience was smooth.'
    }
  ];

}
