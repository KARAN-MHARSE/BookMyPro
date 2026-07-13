import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_ENDPOINTS } from '@core/constants/api-endpoints';
import { Address, CustomerAddressRequest, CustomerProfileResponse } from '@features/customer/models/customer.model';
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerProfileService {
  private http = inject(HttpClient);

  saveProfile(request: FormData) {
    return this.http.post(environment.customerServiceBaseUrl + API_ENDPOINTS.CUSTOMER.CUSTOMER_PROFILE, request);
  }

  getProfile(credentialId: string) {
    return this.http.get<CustomerProfileResponse>(environment.customerServiceBaseUrl + API_ENDPOINTS.CUSTOMER.CUSTOMER_PROFILE + `/${credentialId}`);
  }

  saveOrUpdateCustomeraAddress(request: CustomerAddressRequest) {
    return this.http.post(environment.customerServiceBaseUrl + API_ENDPOINTS.CUSTOMER.CUSTOMER_ADDRESS, request);
  }

  getAddresses(credentialId: string) {
    return this.http.get<Address[]>(
      environment.customerServiceBaseUrl + API_ENDPOINTS.CUSTOMER.CUSTOMER_ADDRESS,
      {
        params: {
          credentialId
        }
      }
    );
  }

  getAddressById(addressId: string) {
  return this.http.get<Address>(
    environment.customerServiceBaseUrl + API_ENDPOINTS.CUSTOMER.CUSTOMER_ADDRESS + `/${addressId}`
  );
}
  constructor() { }
}
