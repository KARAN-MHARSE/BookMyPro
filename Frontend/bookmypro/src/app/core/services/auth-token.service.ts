import { Injectable, inject } from '@angular/core';
import { STORAGE_KEYS } from '../constants/storage-keys';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthTokenService {
  private storage = inject(StorageService);

  getAccessToken(): string | null {
    return this.storage.get(STORAGE_KEYS.ACCESS_TOKEN);
  }

  getRefreshToken(): string | null {
    return this.storage.get(STORAGE_KEYS.REFRESH_TOKEN);
  }

  setTokens(accessToken: string, refreshToken: string): void {
    this.storage.set(STORAGE_KEYS.ACCESS_TOKEN, accessToken);
    this.storage.set(STORAGE_KEYS.REFRESH_TOKEN, refreshToken);
  }

  clearTokens(): void {
    this.storage.remove(STORAGE_KEYS.ACCESS_TOKEN);
    this.storage.remove(STORAGE_KEYS.REFRESH_TOKEN);
  }

  isAuthenticated(): boolean {
    return !!this.getAccessToken();
  }
}
