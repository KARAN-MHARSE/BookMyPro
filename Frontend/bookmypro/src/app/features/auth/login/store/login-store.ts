import { inject, Injectable, signal } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/auth.model';
import { AuthTokenService } from '../../../../core/services/auth-token.service';
import { StorageService } from '../../../../core/services/storage.service';
import { Router } from '@angular/router';

@Injectable()
export class LoginStore {
  private authService = inject(AuthService)
  private authTokenService = inject(AuthTokenService)
  private router = inject(Router)
  private storageService = inject(StorageService)

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

        const roles = response.roles;
        this.storageService.set("roles", JSON.stringify(roles));
        this.storageService.set("credentialId", response.credentialId ?? '')
        this.storageService.set("deviceId", response.deviceId)
        this.loading.set(false);

        if (roles?.includes("CUSTOMER")) {
          this.router.navigate(["/customer"]);
        }
        //  else if (roles?.includes("PROVIDER")) {
        //   this.router.navigate(["/provider"]);
        // } else if (roles?.includes("ADMIN")) {
        //   this.router.navigate(["/admin"]);
        // }
      },
      error: err => {
        this.loading.set(false)
        this.error.set(err.error.detail)
      }
    })
  }
}
