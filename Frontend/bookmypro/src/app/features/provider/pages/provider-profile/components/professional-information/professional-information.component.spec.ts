import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessionalInformationComponent } from './professional-information.component';

describe('ProfessionalInformationComponent', () => {
  let component: ProfessionalInformationComponent;
  let fixture: ComponentFixture<ProfessionalInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfessionalInformationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfessionalInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
