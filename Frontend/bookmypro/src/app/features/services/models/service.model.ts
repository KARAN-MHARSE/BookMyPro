export interface ServiceSummary {
  serviceId: string;
  serviceName: string;
  slug: string;
  serviceCategoryName: string;
  serviceThumbnailUrl: string;
  averageRating: number;
  totalReviews: number;
  providerCount: number;
  startingPrice: number;
  estimatedDuration: number;
}

export interface SortOption {
  value: string;
  label: string;
}

export interface MarketplaceCategory {
  id: number;
  name: string;
  iconUrl?: string;
}

export interface ServiceDetailsResponse {
  service: ServiceDetails;
  providers: ProviderSummary[];
  reviews: Review[];
  faqs: Faq[];
  gallery: GalleryImage[];
}

export interface ServiceDetails {
  serviceId: string;
  serviceName: string;
  slug: string;
  categoryId: string;
  categoryName: string;
  description: string;
  thumbnailUrl: string;
  bannerUrl: string;
  averageRating: number;
  totalReviews: number;
  providerCount: number;
  startingPrice: number;
  estimatedDuration: number;
  includedItems: string[];
}

export interface ProviderSummary {
  providerId: string;
  fullName: string;
  profileImage: string;
  averageRating: number;
  totalReviews: number;
  experienceYears: number;
  completedJobs: number;
  distance?: number | null;
  nextAvailableSlot: string;
  minimumPrice: number;
  verified: boolean;
}

export interface Review {
  reviewId: string;
  customerName: string;
  customerImage: string;
  rating: number;
  review: string;
  reviewDate: string;
}

export interface Faq {
  faqId: string;
  question: string;
  answer: string;
}

export interface GalleryImage {
  imageId: string;
  imageUrl: string;
}