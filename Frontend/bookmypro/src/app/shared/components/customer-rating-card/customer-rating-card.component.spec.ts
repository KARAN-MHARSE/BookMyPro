import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerRatingCardComponent } from './customer-rating-card.component';

describe('CustomerRatingCardComponent', () => {
  let component: CustomerRatingCardComponent;
  let fixture: ComponentFixture<CustomerRatingCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomerRatingCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomerRatingCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
