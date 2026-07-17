import { Component, Input } from '@angular/core';
import { ServiceDetails } from '@app/features/services/models/service.model';

@Component({
  selector: 'app-service-description',
  templateUrl: './service-description.component.html',
  styleUrl: './service-description.component.css'
})
export class ServiceDescriptionComponent {
  @Input({ required: true })
  service!: ServiceDetails;
}
