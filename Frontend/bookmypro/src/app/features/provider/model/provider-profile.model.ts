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
