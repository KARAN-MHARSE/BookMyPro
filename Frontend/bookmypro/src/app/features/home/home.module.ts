import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { HomeComponent } from './home.component';
import { LandingComponent } from './landing/landing.component';
import { HeroComponent } from './landing/components/hero/hero.component';
import { CategoriesServicesComponent } from './landing/components/categories-services/categories-services.component';
import { FeaturedServicesComponent } from './landing/components/featured-services/featured-services.component';
import { HowItWorksComponent } from './landing/components/how-it-works/how-it-works.component';
import { BecomeProviderComponent } from './landing/components/become-provider/become-provider.component';
import { CustomerReviewSectionComponent } from './landing/components/customer-review-section/customer-review-section.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    HomeComponent,
    LandingComponent,
    HeroComponent,
    CategoriesServicesComponent,
    FeaturedServicesComponent,
    HowItWorksComponent,
    BecomeProviderComponent,
    CustomerReviewSectionComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    HomeRoutingModule,
    RouterModule,
    SharedModule,
  ],
})
export class HomeModule {}
