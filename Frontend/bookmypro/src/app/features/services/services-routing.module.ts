import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServiceCatalogComponent } from './pages/service-catalog/service-catalog.component';
import { ServiceDetailsComponent } from './pages/service-details/service-details.component';
import { ProviderProfileComponent } from './pages/provider-profile/provider-profile.component';
import { ChooseSlotComponent } from './pages/choose-slot/choose-slot.component';
import { BookingComponent } from './pages/booking/booking.component';

const routes: Routes = [
  {
    path: "",
    component: ServiceCatalogComponent
  },
  {
    path: "details/:serviceId",
    component: ServiceDetailsComponent
  },
  {
    path: "provider/:providerId",
    component: ProviderProfileComponent
  },
  {
    path: "choose-slot",
    component: ChooseSlotComponent
  },
  {
    path: "booking",
    component: BookingComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServicesRoutingModule { }


