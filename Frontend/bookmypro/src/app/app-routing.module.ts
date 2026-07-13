import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo:"provider",
    pathMatch:"full"
    // loadChildren: () =>
    //   import('./features/home/home.module').then((m) => m.HomeModule),
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'customer',
    loadChildren: () =>
      import('./features/customer/customer.module').then((m) => m.CustomerModule),
  },
  {
    path:"provider",
    loadChildren:()=> import('./features/provider/provider.module').then(m=> m.ProviderModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
