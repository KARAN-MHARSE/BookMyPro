import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ServiceSummary } from '../../models/service.model';

@Component({
  selector: 'app-service-card',
  templateUrl: './service-card.component.html',
  styleUrl: './service-card.component.css'
})
export class ServiceCardComponent {
  private static readonly DEFAULT_THUMBNAIL_URL =
    'data:image/svg+xml,' +
    encodeURIComponent(
      '<svg xmlns="http://www.w3.org/2000/svg" width="400" height="240" viewBox="0 0 400 240">' +
        '<rect width="400" height="240" fill="#e2e8f0"/>' +
        '<g fill="none" stroke="#94a3b8" stroke-width="2">' +
          '<rect x="130" y="70" width="140" height="100" rx="8"/>' +
          '<circle cx="170" cy="105" r="10"/>' +
          '<path d="M150 150 L190 115 L220 135 L270 95"/>' +
        '</g>' +
      '</svg>'
    );

  @Input({ required: true })
  service!: ServiceSummary;

  @Output()
  readonly viewProviders =
    new EventEmitter<string>();

  onViewProviders(): void {
    this.viewProviders.emit(this.service.serviceId);
  }

  get thumbnailUrl(): string {
    const url = this.service.serviceThumbnailUrl?.trim();
    return url ? url : ServiceCardComponent.DEFAULT_THUMBNAIL_URL;
  }

  onThumbnailError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (img.src !== ServiceCardComponent.DEFAULT_THUMBNAIL_URL) {
      img.src = ServiceCardComponent.DEFAULT_THUMBNAIL_URL;
    }
  }
}
