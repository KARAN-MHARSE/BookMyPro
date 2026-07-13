export type Gender = 'Male' | 'Female' | 'Other';

export type Language = 'English' | 'Hindi' | 'Spanish';

export interface CustomerProfile{
  firstName: string;
  middleName: string;
  lastName: string;
  dob: string;
  gender: string;
  language: string;
  occupation: string;
  bio: string;
  defaultAddressId: string;
  profilePhoto?:string | null;
}

export interface Address {
  addressId?: string;
  addressType: string;
  addressName: string;
  landmark: string;
  addressLine1: string;
  addressLine2: string;
  city: string;
  district: string;
  state: string;
  country: string;
  postalCode: string;
  defaultAddress: boolean;
  latitude: string;
  longitude: string;
}

export interface CustomerAddressRequest{
   credentialId:string;
   address: Address ;
}

export interface CustomerProfileResponse{
  personalInfo:CustomerProfile,
  defaultAddress:Address,
  lookups:LookupResponse
}

export interface LookupResponse {
  [key: string]: LookupOption[];
}

export interface LookupOption {
  id: string;
  code: string;
  name: string;
}

