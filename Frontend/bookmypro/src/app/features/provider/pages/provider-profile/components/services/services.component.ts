import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ServiceDialogComponent } from './service-dialog/service-dialog.component';

interface ProviderService {

  serviceId: number;

  serviceName: string;

  category: string;

  duration: number;

  price: number;

  active: boolean;

}

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrl: './services.component.css'
})
export class ServicesComponent {


  services: ProviderService[] = [

    {
      serviceId: 1,
      serviceName: 'Fan Installation',
      category: 'Electrician',
      duration: 60,
      price: 399,
      active: true
    },

    {
      serviceId: 2,
      serviceName: 'Switch Board Repair',
      category: 'Electrician',
      duration: 45,
      price: 249,
      active: true
    }

  ];

  constructor(private dialog: MatDialog) { }

  addService(): void {
    const dialogRef = this.dialog.open(ServiceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const nextId = this.services.length > 0 ? Math.max(...this.services.map(s => s.serviceId)) + 1 : 1;
        this.services.push({
          serviceId: nextId,
          ...result
        });
      }
    });
  }

  editService(service: ProviderService): void {
    const dialogRef = this.dialog.open(ServiceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: service
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const idx = this.services.findIndex(s => s.serviceId === service.serviceId);
        if (idx !== -1) {
          this.services[idx] = {
            ...service,
            ...result
          };
        }
      }
    });
  }

  deleteService(service: ProviderService): void {

    this.services = this.services.filter(

      x => x.serviceId !== service.serviceId

    );

  }
}
