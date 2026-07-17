import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProviderProfileComponent } from './pages/provider-profile/provider-profile.component';

const routes: Routes = [
  {
    path: "",
    component: ProviderProfileComponent

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProviderRoutingModule { }
