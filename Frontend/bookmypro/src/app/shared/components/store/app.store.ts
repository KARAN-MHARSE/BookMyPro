import { inject, Injectable, Signal, signal } from '@angular/core';
import { Router } from '@angular/router';
import { authMeResponse } from '@app/features/auth/models/auth.model';



@Injectable({
    providedIn: 'root'
})
export class AppStore {
    private readonly _loading = signal(false);
    private readonly _isAuthorized = signal(false);
    private readonly _authUser = signal<authMeResponse | null>(null);
    private readonly _profileImage = signal<string | null>(null);

    readonly loading = this._loading.asReadonly();
    readonly isAuthorized = this._isAuthorized.asReadonly();
    readonly authUser = this._authUser.asReadonly();
    readonly profileImage = this._profileImage.asReadonly();


    saveAuthUser(user: authMeResponse, isAuthorized: boolean) {
        this._authUser.set(user);
        this._isAuthorized.set(isAuthorized);
    }

    setAuthorization(isAuthorized: boolean) {
        this._isAuthorized.set(isAuthorized);
    }

    setProfileImage(image: string | null) {
        this._profileImage.set(image);
    }

    setLoading(isLoading: boolean) {
        this._loading.set(isLoading);
    }
}
