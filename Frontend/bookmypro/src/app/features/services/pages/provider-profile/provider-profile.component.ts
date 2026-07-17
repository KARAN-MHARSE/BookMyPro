import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProviderService } from '../../services/provider.service';

export interface ServiceOffer {
  title: string;
  duration: string;
  price: number;
  includesSupplies: boolean;
}

export interface Review {
  author: string;
  date: string;
  rating: number;
  avatar: string;
  avatarBg?: string;
  content: string;
}

export interface AvailabilityDay {
  name: string;
  active: boolean;
}

export interface ProviderDetails {
  id: string;
  name: string;
  initials: string;
  isVerified: boolean;
  isTopRated: boolean;
  specialty: string;
  location: string;
  experienceYears: number;
  rating: number;
  reviewCount: number;
  completedJobs: number;
  responseTime: string;
  about: string;
  skills: string[];
  servicesOffered: ServiceOffer[];
  certifications: string[];
  portfolioGradients: string[];
  reviews: Review[];
  availability: AvailabilityDay[];
  workingHours: string;
  startingPrice: number;
}

@Component({
  selector: 'app-provider-profile',
  templateUrl: './provider-profile.component.html',
  styleUrl: './provider-profile.component.css'
})
export class ProviderProfileComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private providerService = inject(ProviderService);

  private defaultAvailability = [
    { name: 'Mon', active: false },
    { name: 'Tue', active: false },
    { name: 'Wed', active: false },
    { name: 'Thu', active: false },
    { name: 'Fri', active: false },
    { name: 'Sat', active: false },
    { name: 'Sun', active: false }
  ];

  provider: ProviderDetails = {
    id: '',
    name: 'Loading...',
    initials: '',
    isVerified: false,
    isTopRated: false,
    specialty: '',
    location: '',
    experienceYears: 0,
    rating: 0.0,
    reviewCount: 0,
    completedJobs: 0,
    responseTime: '',
    about: '',
    skills: [],
    servicesOffered: [],
    certifications: [],
    portfolioGradients: [],
    reviews: [],
    availability: this.defaultAvailability,
    workingHours: '',
    startingPrice: 0
  };

  ngOnInit(): void {
    const providerId = this.route.snapshot.paramMap.get('providerId');
    if (providerId) {
      this.providerService.getProviderDetails(providerId).subscribe({
        next: (data) => {
          if (data) {
            let mappedAvailability = this.defaultAvailability;
            if (data.availability && data.availability.length > 0) {
              const weekDays = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
              mappedAvailability = weekDays.map(dayName => {
                const found = (data.availability as any[]).find((a: any) => {
                  const dbDay = (a.dayOfWeek || '').toLowerCase();
                  const targetDay = dayName.toLowerCase();
                  return dbDay.startsWith(targetDay) || targetDay.startsWith(dbDay);
                });
                return {
                  name: dayName,
                  active: found ? found.isAvailable : false
                };
              });
            }

            let mappedReviews: Review[] = [];
            if (data.reviews) {
              mappedReviews = data.reviews.map((r: any) => {
                const author = r.customerName || 'Client';
                let avatar = 'C';
                if (author && author.length > 0) {
                  avatar = author.substring(0, 1).toUpperCase();
                }

                const gradients = [
                  'linear-gradient(135deg, #F472B6, #F59E0B)',
                  'linear-gradient(135deg, #3B82F6, #8B5CF6)',
                  'linear-gradient(135deg, #10B981, #3B82F6)',
                  'linear-gradient(135deg, #EF4444, #F59E0B)'
                ];
                const authorCode = author.split('').reduce((acc: number, char: string) => acc + char.charCodeAt(0), 0);
                const avatarBg = gradients[authorCode % gradients.length];

                return {
                  author: author,
                  date: r.reviewDate ? new Date(r.reviewDate).toLocaleDateString() : 'Recent',
                  rating: r.rating || 5,
                  avatar: avatar,
                  avatarBg: avatarBg,
                  content: r.review || ''
                };
              });
            }

            this.provider = {
              ...data,
              availability: mappedAvailability,
              reviews: mappedReviews
            };
          }
        },
        error: (err) => {
          console.error('Error fetching provider details', err);
        }
      });
    }
  }

  getStars(rating: number): string {
    const fullStars = Math.floor(rating);
    const hasHalf = rating % 1 >= 0.5;
    return '★'.repeat(fullStars) + (hasHalf ? '★' : '') + '☆'.repeat(5 - fullStars - (hasHalf ? 1 : 0));
  }
}
