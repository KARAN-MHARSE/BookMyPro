import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AppStore } from '../components/store/app.store';
import { authMeResponse } from '@app/features/auth/models/auth.model';

export const AuthRoleGuard: CanActivateFn = (route, state) => {
  const appStore = inject(AppStore)
  const router = inject(Router)

  const user:authMeResponse | null = appStore.authUser();

  if (!user) {
    return router.createUrlTree(['/auth/login']);
  }

  const allowedRoles = route.data['role'] as string[];
  if (!allowedRoles || allowedRoles.length === 0) {
    return true;
  }

  const hasRole = user?.roles?.some(role =>
    allowedRoles.includes(role)
  );

  if(hasRole) return true;

  return router.createUrlTree(['/auth/login']);
};
