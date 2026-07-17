import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceGalleryComponent } from './service-gallery.component';

describe('ServiceGalleryComponent', () => {
  let component: ServiceGalleryComponent;
  let fixture: ComponentFixture<ServiceGalleryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ServiceGalleryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ServiceGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
