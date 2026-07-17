import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { API_ENDPOINTS } from '../../../core/constants/api-endpoints';
import { PersonalInfoResponse, ProfessionalInfoResponse, EducationResponse, ExperienceResponse, BankInfoResponse, ServiceResponse, AvailabilityResponse, ServiceResponseWithLookups } from '../models/provider-profile.model';

@Injectable({
  providedIn: 'root'
})
export class ProviderProfileService {
  private http = inject(HttpClient);

  getPersonalInfo(credentialId: string): Observable<PersonalInfoResponse> {
    return this.http.get<PersonalInfoResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.PERSONAL_PROFILE}/${credentialId}`
    );
  }

  savePersonalInfo(credentialId: string, request: any): Observable<PersonalInfoResponse> {
    return this.http.post<PersonalInfoResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.PERSONAL_PROFILE}/${credentialId}`,
      request
    );
  }

  getProfessionalInfo(credentialId: string): Observable<ProfessionalInfoResponse> {
    return this.http.get<ProfessionalInfoResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.PROFESSIONAL_PROFILE}/${credentialId}`
    );
  }

  saveProfessionalInfo(credentialId: string, request: any): Observable<ProfessionalInfoResponse> {
    return this.http.post<ProfessionalInfoResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.PROFESSIONAL_PROFILE}/${credentialId}`,
      request
    );
  }

  getEducations(credentialId: string): Observable<EducationResponse[]> {
    return this.http.get<EducationResponse[]>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.EDUCATION}/provider/${credentialId}`
    );
  }

  saveEducation(request: any): Observable<EducationResponse> {
    return this.http.post<EducationResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.EDUCATION}`,
      request
    );
  }

  deleteEducation(educationId: string): Observable<void> {
    return this.http.delete<void>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.EDUCATION}/${educationId}`
    );
  }

  getExperiences(credentialId: string): Observable<ExperienceResponse[]> {
    return this.http.get<ExperienceResponse[]>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.EXPERIENCE}/provider/${credentialId}`
    );
  }

  saveExperience(request: any): Observable<ExperienceResponse> {
    return this.http.post<ExperienceResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.EXPERIENCE}`,
      request
    );
  }

  deleteExperience(experienceId: string): Observable<void> {
    return this.http.delete<void>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.EXPERIENCE}/${experienceId}`
    );
  }

  getBankDetails(credentialId: string): Observable<BankInfoResponse> {
    return this.http.get<BankInfoResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.BANK}/${credentialId}`
    );
  }

  saveBankDetails(credentialId: string, request: any): Observable<BankInfoResponse> {
    return this.http.post<BankInfoResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.BANK}/${credentialId}`,
      request
    );
  }

  getProviderServices(credentialId: string): Observable<ServiceResponseWithLookups> {
    return this.http.get<ServiceResponseWithLookups>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.SERVICES}/${credentialId}`
    );
  }

  saveProviderService(request: any): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.SERVICES}`,
      request
    );
  }

  deleteProviderService(providerServiceId: string): Observable<void> {
    return this.http.delete<void>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.SERVICES}/${providerServiceId}`
    );
  }

  getAvailabilityDetails(credentialId: string): Observable<AvailabilityResponse> {
    return this.http.get<AvailabilityResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.AVAILABILITY}/${credentialId}`
    );
  }

  saveAvailabilityDetails(credentialId: string, request: any): Observable<AvailabilityResponse> {
    return this.http.post<AvailabilityResponse>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.AVAILABILITY}/${credentialId}`,
      request
    );
  }

  updateProfilePhoto(credentialId: string, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(
      `${environment.providerServiceBaseUrl}${API_ENDPOINTS.PROVIDER.PERSONAL_PROFILE}/${credentialId}/photo`,
      formData
    );
  }
}
