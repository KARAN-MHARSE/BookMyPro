import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerProfileComponent } from './pages/customer-profile/customer-profile.component';
import { PersonalInformationComponent } from './pages/customer-profile/personal-information/personal-information.component';
import { SideInformationComponent } from './pages/customer-profile/side-information/side-information.component';
import { AddressInformationDetailComponent } from './pages/customer-profile/address-information-detail/address-information-detail.component';
import { AddressComponent } from './pages/customer-profile/address/address.component';
import { SharedModule } from '@shared/shared.module';
import { LookUpStore } from './store/look-up.store';

@NgModule({
  declarations: [
    CustomerProfileComponent,
    PersonalInformationComponent,
    AddressInformationDetailComponent,
    SideInformationComponent,
    AddressComponent,
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    SharedModule,
    ReactiveFormsModule,
  ],
  providers: [LookUpStore],
})
export class CustomerModule {}
