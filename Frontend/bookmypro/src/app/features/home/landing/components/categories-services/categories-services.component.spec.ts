import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesServicesComponent } from './categories-services.component';

describe('CategoriesServicesComponent', () => {
  let component: CategoriesServicesComponent;
  let fixture: ComponentFixture<CategoriesServicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoriesServicesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CategoriesServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
