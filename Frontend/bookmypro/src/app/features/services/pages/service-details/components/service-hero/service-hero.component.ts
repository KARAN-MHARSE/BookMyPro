import { Component, Input } from '@angular/core';
import { ServiceDetails } from '@app/features/services/models/service.model';

@Component({
  selector: 'app-service-hero',
  templateUrl: './service-hero.component.html',
  styleUrl: './service-hero.component.css'
})
export class ServiceHeroComponent {
  @Input()
  service!: ServiceDetails | null;
}
