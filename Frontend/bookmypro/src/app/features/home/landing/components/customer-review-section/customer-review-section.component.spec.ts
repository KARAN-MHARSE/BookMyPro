import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerReviewSectionComponent } from './customer-review-section.component';

describe('CustomerReviewSectionComponent', () => {
  let component: CustomerReviewSectionComponent;
  let fixture: ComponentFixture<CustomerReviewSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomerReviewSectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomerReviewSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
