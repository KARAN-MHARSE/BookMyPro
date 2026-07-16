import { ScrollStrategyOptions } from "@angular/cdk/overlay";

export interface ServiceSummary {

    id: number;

    name: string;

    slug: string;

    categoryName: string;

    thumbnailUrl: string;

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