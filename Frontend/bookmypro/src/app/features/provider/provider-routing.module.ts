import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProviderProfilePage } from './pages/provider-profile/provider-profile.page';

const routes: Routes = [
  {
    path:"",
    component:ProviderProfilePage

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProviderRoutingModule { }
