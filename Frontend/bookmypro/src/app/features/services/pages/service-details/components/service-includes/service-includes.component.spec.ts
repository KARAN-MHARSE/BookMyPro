import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceIncludesComponent } from './service-includes.component';

describe('ServiceIncludesComponent', () => {
  let component: ServiceIncludesComponent;
  let fixture: ComponentFixture<ServiceIncludesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ServiceIncludesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ServiceIncludesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
