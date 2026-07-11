import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DynamicUiModule } from '../dynamic-ui/dynamic-ui.module';
import { CardComponent } from './components/card/card.component';
import { ProviderCardComponent } from './components/provider-card/provider-card.component';
import { ServiceCardComponent } from './components/service-card/service-card.component';
import { StatCardComponent } from './components/stat-card/stat-card.component';
import { CustomerRatingCardComponent } from './components/customer-rating-card/customer-rating-card.component';
import { CategoryCardComponent } from './components/category-card/category-card.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { RouterModule } from '@angular/router';

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
  CustomerRatingCardComponent
],
  declarations: [
    CardComponent,
    ProviderCardComponent,
    ServiceCardComponent,
    StatCardComponent,
    CustomerRatingCardComponent,
    CategoryCardComponent,
    NavbarComponent,
  ],
})
export class SharedModule {}
