import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderListingComponent } from './provider-listing.component';

describe('ProviderListingComponent', () => {
  let component: ProviderListingComponent;
  let fixture: ComponentFixture<ProviderListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProviderListingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProviderListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
