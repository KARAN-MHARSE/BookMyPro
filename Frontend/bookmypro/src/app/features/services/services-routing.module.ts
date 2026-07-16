import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServiceCatalogComponent } from './pages/service-catalog/service-catalog.component';

const routes: Routes = [
  {
    path: "",
    component: ServiceCatalogComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServicesRoutingModule { }
