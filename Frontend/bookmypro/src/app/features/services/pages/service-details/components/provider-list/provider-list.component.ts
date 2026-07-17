import { Component, Input } from '@angular/core';
import { ProviderSummary } from '@app/features/services/models/service.model';

@Component({
  selector: 'app-provider-list',
  templateUrl: './provider-list.component.html',
  styleUrl: './provider-list.component.css'
})
export class ProviderListComponent {
  @Input({ required: true })
  providers: ProviderSummary[] = [];

  sortBy = 'recommended';

  trackByProvider(index: number, provider: ProviderSummary): string {
    return provider.providerId;
  }
}
