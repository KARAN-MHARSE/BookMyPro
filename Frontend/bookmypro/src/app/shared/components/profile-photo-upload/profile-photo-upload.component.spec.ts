import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePhotoUploadComponent } from './profile-photo-upload.component';

describe('ProfilePhotoUploadComponent', () => {
  let component: ProfilePhotoUploadComponent;
  let fixture: ComponentFixture<ProfilePhotoUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilePhotoUploadComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfilePhotoUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
