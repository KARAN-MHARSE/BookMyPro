import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryCardComponent } from './category-card.component';

describe('CategoryCardComponent', () => {
  let component: CategoryCardComponent;
  let fixture: ComponentFixture<CategoryCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoryCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CategoryCardComponent);
    component = fixture.componentInstance;
    component.category = { name: 'Cleaning', icon: '🧹', providers: 1240 };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
