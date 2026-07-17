import { Component, effect, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AppStore } from '../../core/store/app.store';
import { ProviderProfileService } from '../../features/provider/services/provider-profile.service';
import { CustomerProfileService } from '../../features/customer/services/customer.profile.service';
import { AuthTokenService } from '@core/services/auth-token.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  appStore = inject(AppStore);
  isLoggedIn = false;
  isMenuOpen = false;

  private providerProfileService = inject(ProviderProfileService);
  private customerProfileService = inject(CustomerProfileService);
  private authTokenService = inject(AuthTokenService);
  private router = inject(Router);

  constructor() {
    effect(() => {
      this.isLoggedIn = this.appStore.isAuthorized();
      const user = this.appStore.authUser();

      if (this.isLoggedIn && user) {
        const roles = user.roles || [];
        const isProvider = roles.includes('ROLE_PROVIDER') || roles.includes('PROVIDER');
        const isCustomer = roles.includes('ROLE_CUSTOMER') || roles.includes('CUSTOMER');

        // Only fetch initial photo if the store signal is empty
        if (!this.appStore.profileImage()) {
          if (isProvider) {
            this.providerProfileService.getPersonalInfo(user.credentialId).subscribe({
              next: (res) => {
                if (res && res.profilePhoto) {
                  this.appStore.setProfileImage(res.profilePhoto);
                }
              },
              error: (err) => {
                console.error('Navbar failed to load provider photo', err);
              }
            });
          } else if (isCustomer) {
            this.customerProfileService.getProfile(user.credentialId).subscribe({
              next: (res) => {
                if (res && res.personalInfo && res.personalInfo.profilePhoto) {
                  this.appStore.setProfileImage(res.personalInfo.profilePhoto);
                }
              },
              error: (err) => {
                console.error('Navbar failed to load customer photo', err);
              }
            });
          }
        }
      } else {
        this.appStore.setProfileImage(null);
      }
    });
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  closeMenu(): void {
    this.isMenuOpen = false;
  }

  navigateToProfile(): void {
    this.closeMenu();
    const user = this.appStore.authUser();
    if (user) {
      const roles = user.roles || [];
      const isProvider = roles.includes('ROLE_PROVIDER') || roles.includes('PROVIDER');
      if (isProvider) {
        this.router.navigate(['/provider']);
      } else {
        this.router.navigate(['/customer']);
      }
    }
  }

  logout(): void {
    this.authTokenService.clearTokens();
    this.appStore.setAuthorization(false);
    this.appStore.saveAuthUser(null as any, false);
    this.closeMenu();
    this.router.navigate(['/auth']);
  }
}
