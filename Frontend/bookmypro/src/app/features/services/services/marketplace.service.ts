import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_ENDPOINTS } from '@app/core/constants/api-endpoints';
import { environment } from '@env/environment';
import { ServiceSummary, ServiceDetailsResponse } from '../models/service.model';

@Injectable({
  providedIn: 'root'
})
export class MarketplaceService {
  private readonly http = inject(HttpClient);

  getServicesGridList() {
    return this.http.get<ServiceSummary[]>(
      environment.marketplaceBaseUrl + API_ENDPOINTS.SERVICES.SUMMARY_GRID
    );
  }

  getServiceDetails(serviceId: string) {
    return this.http.get<ServiceDetailsResponse>(
      `${environment.marketplaceBaseUrl}${API_ENDPOINTS.SERVICES.DETAILS}/${serviceId}`
    );
  }
}
