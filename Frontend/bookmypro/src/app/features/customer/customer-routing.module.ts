import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerProfileComponent } from './pages/customer-profile/customer-profile.component';
import { AddressInformationDetailComponent } from './pages/customer-profile/address-information-detail/address-information-detail.component';

const routes: Routes = [
  {
    path: '',
    component: CustomerProfileComponent,
  },
  {
    path: 'address',
    component: AddressInformationDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CustomerRoutingModule {}
