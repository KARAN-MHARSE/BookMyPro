import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { CustomerRegisterComponent } from './customer-register/customer-register.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginStore } from './login/store/login-store';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { RegisterComponent } from './register/register.component';
import { PasswordInputComponent } from './forgot-password/password-input/password-input.component';
import { ForgotPasswordOtpVerificationComponent } from './forgot-password/forgot-password-otp-verification/forgot-password-otp-verification.component';
import { ForgotPasswordMessageComponent } from './forgot-password/forgot-password-message/forgot-password-message.component';


@NgModule({
  declarations: [
    LoginComponent,
    CustomerRegisterComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent,
    RegisterComponent,
    PasswordInputComponent,
    ForgotPasswordOtpVerificationComponent,
    ForgotPasswordMessageComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    SharedModule,
  ],
  providers: [LoginStore],
})
export class AuthModule { }
