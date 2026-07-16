import { Component, Input, OnInit, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ServiceDialogComponent } from './service-dialog/service-dialog.component';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { ServiceResponse } from '../../../../model/provider-profile.model';

interface ProviderService {
  providerServiceId?: string;
  serviceId: string;
  serviceName: string;
  category: string;
  duration: number;
  price: number;
  active: boolean;
  serviceRealName?: string;
  categoryName?: string;
  experienceYears?: number;
  priceType?: string;
  homeService?: boolean;
  emergencyService?: boolean;
  weekendAvailable?: boolean;
  minimumPrice?: number;
  maximumPrice?: number;
}

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrl: './services.component.css'
})
export class ServicesComponent implements OnInit {
  @Input()
  credentialId = '';

  services: ProviderService[] = [];

  private dialog = inject(MatDialog);
  private profileService = inject(ProviderProfileService);

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    if (this.credentialId) {
      this.profileService.getProviderServices(this.credentialId).subscribe({
        next: (res: ServiceResponse[]) => {
          this.services = res.map(s => ({
            providerServiceId: s.providerServiceId,
            serviceId: s.serviceId,
            serviceName: s.serviceId,
            category: s.categoryId,
            duration: s.estimatedDuration,
            price: s.basePrice ? Number(s.basePrice) : 0,
            active: s.active ?? false,
            serviceRealName: s.serviceRealName,
            categoryName: s.categoryName,
            experienceYears: s.experienceYears,
            priceType: s.priceType,
            homeService: s.homeService,
            emergencyService: s.emergencyService,
            weekendAvailable: s.weekendAvailable,
            minimumPrice: s.minimumPrice ? Number(s.minimumPrice) : undefined,
            maximumPrice: s.maximumPrice ? Number(s.maximumPrice) : undefined
          }));
        },
        error: (err: any) => {
          console.error('Failed to load provider services list', err);
        }
      });
    }
  }

  addService(): void {
    const dialogRef = this.dialog.open(ServiceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        const payload = {
          credentialId: this.credentialId,
          serviceId: result.serviceName,
          categoryId: result.category,
          experienceYears: result.experienceYears,
          basePrice: result.price,
          priceType: result.priceType,
          minimumPrice: result.minimumPrice || null,
          maximumPrice: result.maximumPrice || null,
          estimatedDuration: result.duration,
          homeService: result.homeService,
          emergencyService: result.emergencyService,
          weekendAvailable: result.weekendAvailable,
          active: result.active
        };

        this.profileService.saveProviderService(payload).subscribe({
          next: () => {
             this.loadServices();
          },
          error: (err: any) => {
            console.error('Failed to add provider service', err);
            alert('Failed to add service.');
          }
        });
      }
    });
  }

  editService(service: ProviderService): void {
    const dialogRef = this.dialog.open(ServiceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: {
        category: service.category,
        serviceName: service.serviceId,
        price: service.price,
        duration: service.duration,
        active: service.active,
        experienceYears: service.experienceYears,
        priceType: service.priceType,
        homeService: service.homeService,
        emergencyService: service.emergencyService,
        weekendAvailable: service.weekendAvailable,
        minimumPrice: service.minimumPrice,
        maximumPrice: service.maximumPrice
      }
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        const payload = {
          providerServiceId: service.providerServiceId,
          credentialId: this.credentialId,
          serviceId: result.serviceName,
          categoryId: result.category,
          experienceYears: result.experienceYears,
          basePrice: result.price,
          priceType: result.priceType,
          minimumPrice: result.minimumPrice || null,
          maximumPrice: result.maximumPrice || null,
          estimatedDuration: result.duration,
          homeService: result.homeService,
          emergencyService: result.emergencyService,
          weekendAvailable: result.weekendAvailable,
          active: result.active
        };

        this.profileService.saveProviderService(payload).subscribe({
          next: () => {
            this.loadServices();
          },
          error: (err: any) => {
            console.error('Failed to update provider service', err);
            alert('Failed to update service.');
          }
        });
      }
    });
  }

  deleteService(service: ProviderService): void {
    if (service.providerServiceId && confirm('Are you sure you want to delete this service?')) {
      this.profileService.deleteProviderService(service.providerServiceId).subscribe({
        next: () => {
          this.loadServices();
        },
        error: (err: any) => {
          console.error('Failed to delete provider service', err);
          alert('Failed to delete service.');
        }
      });
    }
  }
}
