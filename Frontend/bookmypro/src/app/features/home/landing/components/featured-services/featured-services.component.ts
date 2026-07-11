import { Component } from '@angular/core';

@Component({
  selector: 'app-featured-services',
  templateUrl: './featured-services.component.html',
  styleUrl: './featured-services.component.css'
})
export class FeaturedServicesComponent {
 services = [
    {
      title: 'Deep Home Cleaning',
      icon: '🧼',
      thumbGradient: 'linear-gradient(135deg,#DBEAFE,#EDE9FE)',
      badgeText: 'Bestseller',
      badgeVariant: 'success',
      rating: 4.9,
      reviewCount: 2140,
      priceFrom: 89
    },
    {
      title: 'Bathroom Deep Clean',
      icon: '🚿',
      thumbGradient: 'linear-gradient(135deg,#FEF3C7,#FDE68A)',
      badgeText: 'Popular',
      badgeVariant: 'primary',
      rating: 4.8,
      reviewCount: 1320,
      priceFrom: 59
    },
    {
      title: 'Faucet Repair & Install',
      icon: '🔧',
      thumbGradient: 'linear-gradient(135deg,#DBEAFE,#BFDBFE)',
      badgeText: 'Same Day',
      badgeVariant: 'warning',
      rating: 4.7,
      reviewCount: 860,
      priceFrom: 49
    },
    {
      title: 'Salon At Home',
      icon: '💇',
      thumbGradient: 'linear-gradient(135deg,#FCE7F3,#FBCFE8)',
      badgeText: 'New',
      badgeVariant: 'neutral',
      rating: 4.9,
      reviewCount: 540,
      priceFrom: 39
    }
  ];
}
