import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileStepperComponent } from './profile-stepper.component';

describe('ProfileStepperComponent', () => {
  let component: ProfileStepperComponent;
  let fixture: ComponentFixture<ProfileStepperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileStepperComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfileStepperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
