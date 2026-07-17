import { Component, Input, OnInit, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ServiceDialogComponent } from './service-dialog/service-dialog.component';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { LookupOption, LookupResponse } from '@shared/models/lookup.model';
import { ServiceResponse, ServiceResponseWithLookups, ProviderService } from '../../../../models/provider-profile.model';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrl: './services.component.css'
})
export class ServicesComponent implements OnInit {
  @Input()
  credentialId = '';

  services: ProviderService[] = [];
  lookups: LookupResponse = {};

  private dialog = inject(MatDialog);
  private profileService = inject(ProviderProfileService);

  ngOnInit(): void {
    this.loadServices();
  }

  loadServices(): void {
    if (this.credentialId) {
      this.profileService.getProviderServices(this.credentialId).subscribe({
        next: (res: ServiceResponseWithLookups) => {
          this.lookups = res.lookups ?? {};

          const serviceLookups = this.lookups['SERVICE'] ?? [];
          const categoryLookups = this.lookups['SERVICE_CATEGORY'] ?? [];

          this.services = res.services.map((service) => {
            const serviceName = this.getLookupName(serviceLookups, service.serviceId);
            const categoryName = this.getLookupName(categoryLookups, service.categoryId);

            return {
              providerServiceId: service.providerServiceId,
              serviceId: service.serviceId,
              serviceName,
              category: service.categoryId,
              duration: service.estimatedDuration,
              price: service.basePrice ? Number(service.basePrice) : 0,
              active: service.active ?? false,
              serviceRealName: serviceName,
              categoryName,
              experienceYears: service.experienceYears,
              priceType: service.priceType,
              homeService: service.homeService,
              emergencyService: service.emergencyService,
              weekendAvailable: service.weekendAvailable,
              minimumPrice: service.minimumPrice ? Number(service.minimumPrice) : undefined,
              maximumPrice: service.maximumPrice ? Number(service.maximumPrice) : undefined
            };
          });
        },
        error: (err: any) => {
          console.error('Failed to load provider services list', err);
        }
      });
    }
  }

  private getLookupName(options: LookupOption[], id?: string): string {
    if (!id) {
      return '';
    }

    return options.find((option) => option.id === id)?.name ?? id;
  }

  addService(): void {
    const dialogRef = this.dialog.open(ServiceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: {
        service: null,
        lookups: this.lookups
      }
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
        service: {
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
        },
        lookups: this.lookups
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
