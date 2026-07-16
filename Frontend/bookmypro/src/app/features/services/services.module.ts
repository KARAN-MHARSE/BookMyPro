import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServicesRoutingModule } from './services-routing.module';
import { ServiceCatalogComponent } from './pages/service-catalog/service-catalog.component';
import { ServiceDetailsComponent } from './pages/service-details/service-details.component';
import { ProviderListingComponent } from './pages/provider-listing/provider-listing.component';
import { ProviderProfileComponent } from './pages/provider-profile/provider-profile.component';
import { ChooseSlotComponent } from './pages/choose-slot/choose-slot.component';
import { BookingComponent } from './pages/booking/booking.component';
import { ServiceCardComponent } from './components/service-card/service-card.component';
import { ServiceSearchComponent } from './components/service-search/service-search.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CategoryFilterComponent } from './components/category-filter/category-filter.component';
import { SortDropdownComponent } from './components/sort-dropdown/sort-dropdown.component';


@NgModule({
  declarations: [
    ServiceCatalogComponent,
    ServiceDetailsComponent,
    ProviderListingComponent,
    ProviderProfileComponent,
    ChooseSlotComponent,
    BookingComponent,
    ServiceCardComponent,
    ServiceSearchComponent,
    CategoryFilterComponent,
    SortDropdownComponent
  ],
  imports: [
    CommonModule,
    ServicesRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class ServicesModule { }
