import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BecomeProviderComponent } from './become-provider.component';

describe('BecomeProviderComponent', () => {
  let component: BecomeProviderComponent;
  let fixture: ComponentFixture<BecomeProviderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BecomeProviderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BecomeProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
