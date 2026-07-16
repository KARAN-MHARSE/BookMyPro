import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ServiceSummary } from '../../models/service.model';

@Component({
  selector: 'app-service-card',
  templateUrl: './service-card.component.html',
  styleUrl: './service-card.component.css'
})
export class ServiceCardComponent {
  @Input({ required: true })
  service!: ServiceSummary;

  @Output()
  readonly viewProviders =
    new EventEmitter<number>();

  onViewProviders(): void {
    this.viewProviders.emit(this.service.id);
  }
}
