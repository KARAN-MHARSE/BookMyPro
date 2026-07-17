import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { API_ENDPOINTS } from '../../../core/constants/api-endpoints';
import { ProviderDetails } from '../pages/provider-profile/provider-profile.component';

@Injectable({
  providedIn: 'root'
})
export class ProviderService {
  private http = inject(HttpClient);

  getProviderDetails(providerId: string): Observable<ProviderDetails> {
    return this.http.get<ProviderDetails>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.DETAILS}/${providerId}`
    );
  }
}
