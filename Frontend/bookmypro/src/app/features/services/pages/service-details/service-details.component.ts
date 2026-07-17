import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ServiceDetailsResponse } from '../../models/service.model';
import { MarketplaceService } from '../../services/marketplace.service';

@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrl: './service-details.component.css'
})
export class ServiceDetailsComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly marketplaceService = inject(MarketplaceService);

  service = signal<ServiceDetailsResponse | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    const serviceId = this.route.snapshot.paramMap.get('serviceId');

    if (!serviceId) {
      this.error.set('Service not found.');
      this.loading.set(false);
      return;
    }

    this.marketplaceService.getServiceDetails(serviceId).subscribe({
      next: (response) => {
        this.service.set(this.normalizeResponse(response));
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Unable to load service details. Please try again.');
        this.loading.set(false);
      }
    });
  }

  private normalizeResponse(response: ServiceDetailsResponse): ServiceDetailsResponse {
    return {
      service: {
        ...response.service,
        includedItems: response.service?.includedItems ?? []
      },
      providers: response.providers ?? [],
      reviews: response.reviews ?? [],
      faqs: response.faqs ?? [],
      gallery: response.gallery ?? []
    };
  }
}
