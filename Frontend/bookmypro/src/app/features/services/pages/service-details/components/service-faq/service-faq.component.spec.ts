import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceFaqComponent } from './service-faq.component';

describe('ServiceFaqComponent', () => {
  let component: ServiceFaqComponent;
  let fixture: ComponentFixture<ServiceFaqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ServiceFaqComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ServiceFaqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
