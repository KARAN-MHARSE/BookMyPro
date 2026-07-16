import { Component, inject } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { StorageService } from './core/services/storage.service';
import { AuthTokenService } from './core/services/auth-token.service';
import { AuthService } from './features/auth/services/auth.service';
import { authMeResponse } from './features/auth/models/auth.model';
import { AppStore } from './shared/components/store/app.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'bookmypro';
  isAuthPage = false;

  router = inject(Router);
  authTokenService = inject(AuthTokenService)
  authService = inject(AuthService)
  appStore = inject(AppStore)
  storageService = inject(StorageService)

  ngOnInit(): void {
    this.authMe();
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd)
      )
      .subscribe(event => {
        const url = event.urlAfterRedirects;
        this.isAuthPage = url.includes("auth");
        console.log('Is Auth Page:', this.isAuthPage);
      });
  }

  authMe() {
    const accessToken: string | null = this.authTokenService.getAccessToken();
    const deviceId = this.storageService.get("deviceId");

    if (!accessToken || !deviceId) {
      return;
    }

    this.appStore.setLoading(true);

    this.authService.authMe(deviceId)
      .subscribe({
        next: (res: authMeResponse) => {
          this.appStore.saveAuthUser(res, true)
          this.appStore.setLoading(false);
        },
        error: (err) => {
          this.appStore.setAuthorization(false)
          this.appStore.setLoading(false);
        }
      })


  }

}
