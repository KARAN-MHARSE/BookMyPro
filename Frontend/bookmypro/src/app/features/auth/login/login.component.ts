import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LoginStore } from './store/login-store';
import { StorageService } from '../../../core/services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {

  private fb = inject(FormBuilder);
  private storageService = inject(StorageService)

  constructor(public store: LoginStore,private router:Router) { }

  ngOnInit(): void {
    const roles = this.storageService.get("roles");
    if (roles?.includes("CUSTOMER")) {
      this.router.navigate(["/customer"]);
    }

  }

  form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      Validators.required
    ]
  });

  login() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.store.login({
      email: this.form.value.email!,
      password: this.form.value.password!,
      deviceId: this.storageService.get("deviceId") || '',
      deviceName: navigator.platform,
      browser: navigator.userAgent,
      ipAddress: '',
      location: ''
    });
  }

}


