import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPasswordOtpVerificationComponent } from './forgot-password-otp-verification.component';

describe('ForgotPasswordOtpVerificationComponent', () => {
  let component: ForgotPasswordOtpVerificationComponent;
  let fixture: ComponentFixture<ForgotPasswordOtpVerificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ForgotPasswordOtpVerificationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ForgotPasswordOtpVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
