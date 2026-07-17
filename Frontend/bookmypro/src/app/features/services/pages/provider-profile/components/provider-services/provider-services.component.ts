import { Component, Input } from '@angular/core';
import { ServiceOffer } from '../../provider-profile.component';

@Component({
  selector: 'app-provider-services',
  templateUrl: './provider-services.component.html',
  styleUrl: './provider-services.component.css'
})
export class ProviderServicesComponent {
  @Input({ required: true }) services!: ServiceOffer[];
}
