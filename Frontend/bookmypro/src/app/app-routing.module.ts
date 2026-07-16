import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthRoleGuard } from './shared/guards/auth-role-guard.guard';

const routes: Routes = [
  {
    path: '',
    // redirectTo:"provider",
    // pathMatch:"full"
    loadChildren: () =>
      import('./features/home/home.module').then((m) => m.HomeModule),
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'services',
    loadChildren: () =>
      import('./features/services/services.module').then((m) => m.ServicesModule),
  },
  {
    path: 'customer',
    loadChildren: () =>
      import('./features/customer/customer.module').then((m) => m.CustomerModule),
    canActivate: [AuthRoleGuard],
    data: {
      role: ['CUSTOMER']
    }
  },
  {
    path: "provider",
    loadChildren: () => import('./features/provider/provider.module').then(m => m.ProviderModule),
    canActivate: [AuthRoleGuard],
    data: {
      role: ['PROVIDER']
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
