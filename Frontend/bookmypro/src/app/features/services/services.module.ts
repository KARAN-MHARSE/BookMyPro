import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServicesRoutingModule } from './services-routing.module';
import { ServiceCatalogComponent } from './pages/service-catalog/service-catalog.component';
import { ServiceDetailsComponent } from './pages/service-details/service-details.component';
import { ProviderListingComponent } from './pages/provider-listing/provider-listing.component';
import { ProviderProfileComponent } from './pages/provider-profile/provider-profile.component';
import { ProviderHeaderComponent } from './pages/provider-profile/components/provider-header/provider-header.component';
import { ProviderServicesComponent } from './pages/provider-profile/components/provider-services/provider-services.component';
import { ProviderReviewsComponent } from './pages/provider-profile/components/provider-reviews/provider-reviews.component';
import { ChooseSlotComponent } from './pages/choose-slot/choose-slot.component';
import { BookingComponent } from './pages/booking/booking.component';
import { CheckoutAddressComponent } from './pages/booking/components/checkout-address/checkout-address.component';
import { CheckoutBookingDetailsComponent } from './pages/booking/components/checkout-booking-details/checkout-booking-details.component';
import { CheckoutPaymentComponent } from './pages/booking/components/checkout-payment/checkout-payment.component';
import { CheckoutSummaryComponent } from './pages/booking/components/checkout-summary/checkout-summary.component';
import { ServiceCardComponent } from './components/service-card/service-card.component';
import { ServiceSearchComponent } from './components/service-search/service-search.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CategoryFilterComponent } from './components/category-filter/category-filter.component';
import { SortDropdownComponent } from './components/sort-dropdown/sort-dropdown.component';
import { ServiceHeroComponent } from './pages/service-details/components/service-hero/service-hero.component';
import { ServiceDescriptionComponent } from './pages/service-details/components/service-description/service-description.component';
import { ServiceIncludesComponent } from './pages/service-details/components/service-includes/service-includes.component';
import { ServiceGalleryComponent } from './pages/service-details/components/service-gallery/service-gallery.component';
import { ServiceFaqComponent } from './pages/service-details/components/service-faq/service-faq.component';
import { ServiceReviewsComponent } from './pages/service-details/components/service-reviews/service-reviews.component';
import { ProviderCardComponent } from './pages/service-details/components/provider-card/provider-card.component';
import { ProviderListComponent } from './pages/service-details/components/provider-list/provider-list.component';

@NgModule({
  declarations: [
    ServiceCatalogComponent,
    ServiceDetailsComponent,
    ProviderListingComponent,
    ProviderProfileComponent,
    ProviderHeaderComponent,
    ProviderServicesComponent,
    ProviderReviewsComponent,
    ChooseSlotComponent,
    BookingComponent,
    CheckoutAddressComponent,
    CheckoutBookingDetailsComponent,
    CheckoutPaymentComponent,
    CheckoutSummaryComponent,
    ServiceCardComponent,
    ServiceSearchComponent,
    CategoryFilterComponent,
    SortDropdownComponent,
    ServiceHeroComponent,
    ServiceDescriptionComponent,
    ServiceIncludesComponent,
    ServiceGalleryComponent,
    ServiceFaqComponent,
    ServiceReviewsComponent,
    ProviderCardComponent,
    ProviderListComponent
  ],
  imports: [
    CommonModule,
    ServicesRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class ServicesModule { }


