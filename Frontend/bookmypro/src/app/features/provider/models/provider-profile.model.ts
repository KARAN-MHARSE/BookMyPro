import { LookupResponse } from '@shared/models/lookup.model';

export interface PersonalInfoResponse {
  providerId: string;
  credentialId: string;
  profilePhoto?: string;
  firstName: string;
  middleName?: string;
  lastName: string;
  dob?: string;
  gender?: string;
  language?: string;
  phoneNumber: string;
  bio?: string;
}

export interface ProfessionalInfoResponse {
  providerId: string;
  credentialId: string;
  professionalTitle: string;
  providerType: string;
  experience: number;
  businessName?: string;
  workingSince?: string;
  gstNumber?: string;
  panNumber?: string;
  website?: string;
  portfolio?: string;
  professionalSummary?: string;
}

export interface EducationResponse {
  educationId?: string;
  providerId?: string;
  institutionName: string;
  degree: string;
  specialization?: string;
  startYear: string;
  endYear?: string;
  currentlyStudying: boolean;
  description?: string;
  year?: string;
}

export interface ExperienceResponse {
  experienceId?: string;
  providerId?: string;
  companyName: string;
  designation: string;
  startDate: string;
  endDate?: string;
  currentlyWorking: boolean;
  description?: string;
  company?: string;
  current?: boolean;
}

export interface BankInfoResponse {
  bankId?: string;
  providerId?: string;
  accountHolderName: string;
  bankName: string;
  accountNumber: string;
  confirmAccountNumber?: string;
  ifscCode: string;
  branchName?: string;
  accountType?: string;
  upiId?: string;
  panNumber?: string;
  gstNumber?: string;
  isPrimary?: boolean;
  verificationStatus?: string;
}

export interface ServiceResponseWithLookups {
  services: ServiceResponse[];
  lookups: LookupResponse;
}

export interface ServiceResponse {
  providerServiceId?: string;
  providerId?: string;
  serviceId: string;
  categoryId: string;
  experienceYears?: number;
  basePrice: number;
  priceType: string;
  minimumPrice?: number;
  maximumPrice?: number;
  estimatedDuration: number;
  homeService?: boolean;
  emergencyService?: boolean;
  weekendAvailable?: boolean;
  status?: string;
  active?: boolean;
  categoryName?: string;
  serviceRealName?: string;
}

export interface AvailabilityResponse {
  workingDays: string[];
  startTime: string;
  endTime: string;
  breakStart: string;
  breakEnd: string;
  serviceRadius: number;
  maxBookings: number;
  homeService: boolean;
  emergencyService: boolean;
  weekendAvailable: boolean;
}

export interface ProviderService {
  providerServiceId?: string;
  serviceId: string;
  serviceName: string;
  category: string;
  duration: number;
  price: number;
  active: boolean;
  serviceRealName?: string;
  categoryName?: string;
  experienceYears?: number;
  priceType?: string;
  homeService?: boolean;
  emergencyService?: boolean;
  weekendAvailable?: boolean;
  minimumPrice?: number;
  maximumPrice?: number;
}

export interface CategoryOption {
  id: string;
  name: string;
}

export interface ServiceOption {
  id: string;
  name: string;
  parentValue?: string;
}

export interface ServiceDialogData {
  service?: {
    category?: string;
    serviceName?: string;
    price?: number | string;
    duration?: number | string;
    active?: boolean;
    experienceYears?: number | string;
    priceType?: string;
    homeService?: boolean;
    emergencyService?: boolean;
    weekendAvailable?: boolean;
    minimumPrice?: number | string;
    maximumPrice?: number | string;
  };
  lookups?: LookupResponse;
}

export interface ProviderDocument {
  documentId: number;
  documentType: string;
  documentNumber: string;
  status: string;
  uploadDate: string;
  issueDate?: string;
  expiryDate?: string;
}
