import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DynamicUiModule } from '@app/dynamic-ui/dynamic-ui.module';
import { CardComponent } from './components/card/card.component';
import { ProviderCardComponent } from './components/provider-card/provider-card.component';
import { ServiceCardComponent } from './components/service-card/service-card.component';
import { StatCardComponent } from './components/stat-card/stat-card.component';
import { CustomerRatingCardComponent } from './components/customer-rating-card/customer-rating-card.component';
import { CategoryCardComponent } from './components/category-card/category-card.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { ProfilePhotoUploadComponent } from './components/profile-photo-upload/profile-photo-upload.component';
import { AddressTypeSelectorComponent } from './components/address-type-selector/address-type-selector.component';
import { ProgressCardComponent } from './components/progress-card/progress-card.component';
import { MapPreviewComponent } from './components/map-preview/map-preview.component';
import { SectionCardComponent } from './components/section-card/section-card.component';
import { ProfileStepperComponent } from './components/profile-stepper/profile-stepper.component';
import { StepperCardComponent } from './components/stepper-card/stepper-card.component';

@NgModule({
  imports: [CommonModule, DynamicUiModule,RouterModule],
  exports: [
  CommonModule,
  DynamicUiModule,
  CategoryCardComponent,
  ServiceCardComponent,
  CardComponent,
  ProviderCardComponent,
  StatCardComponent,
  NavbarComponent,
  CustomerRatingCardComponent,
  StepperCardComponent
],
  declarations: [
    CardComponent,
    ProviderCardComponent,
    ServiceCardComponent,
    StatCardComponent,
    CustomerRatingCardComponent,
    CategoryCardComponent,
    NavbarComponent,
    ProfilePhotoUploadComponent,
    AddressTypeSelectorComponent,
    ProgressCardComponent,
    MapPreviewComponent,
    SectionCardComponent,
    ProfileStepperComponent,
    StepperCardComponent,
  ],
})
export class SharedModule {}
