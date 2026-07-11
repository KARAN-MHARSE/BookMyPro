import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeaturedServicesComponent } from './featured-services.component';

describe('FeaturedServicesComponent', () => {
  let component: FeaturedServicesComponent;
  let fixture: ComponentFixture<FeaturedServicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeaturedServicesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeaturedServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
