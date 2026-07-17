import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ChooseSlotComponent } from './choose-slot.component';

describe('ChooseSlotComponent', () => {
  let component: ChooseSlotComponent;
  let fixture: ComponentFixture<ChooseSlotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ChooseSlotComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ChooseSlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

