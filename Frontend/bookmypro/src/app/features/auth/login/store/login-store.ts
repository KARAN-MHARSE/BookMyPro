import { inject, Injectable, signal } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { authMeResponse, LoginRequest } from '../../models/auth.model';
import { AuthTokenService } from '../../../../core/services/auth-token.service';
import { StorageService } from '../../../../core/services/storage.service';
import { Router } from '@angular/router';
import { AppStore } from '@app/shared/components/store/app.store';

@Injectable()
export class LoginStore {
  private authService = inject(AuthService)
  private authTokenService = inject(AuthTokenService)
  private router = inject(Router)
  private storageService = inject(StorageService)
  private appStore = inject(AppStore)

  loading = signal(false);
  error = signal('')

  login(request: LoginRequest) {
    this.loading.set(true);

    this.authService.login(request).subscribe({
      next: (response) => {
        this.error.set('')
        this.authTokenService.setTokens(
          response.accessToken,
          response.refreshToken ?? ''
        );

        this.storageService.set("deviceId", response.deviceId)

        let authUser: authMeResponse = {
          email: response.email,
          credentialId: response.credentialId,
          roles: response.roles,
          deviceId: response.deviceId
        }

        this.appStore.saveAuthUser(authUser, true);

        this.loading.set(false);

        if (response.roles?.includes("CUSTOMER")) {
          this.router.navigate(["/customer"]);
        }
        else if (response.roles?.includes("PROVIDER")) {
          this.router.navigate(["/provider"]);
        } else if (response.roles?.includes("ADMIN")) {
          this.router.navigate(["/admin"]);
        }
      },
      error: err => {
        this.loading.set(false)
        this.error.set(err.error.detail)
      }
    })
  }
}
