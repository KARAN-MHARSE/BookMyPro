import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { MarketplaceCategory } from '../../models/service.model';

@Component({
  selector: 'app-category-filter',
  templateUrl: './category-filter.component.html',
  styleUrl: './category-filter.component.css'
})
export class CategoryFilterComponent {
  @Input({ required: true })
  categories: MarketplaceCategory[] = [];

  @Output()
  readonly categoryChanged = new EventEmitter<number | null>();

  readonly selectedCategory = signal<number | null>(null);

  selectCategory(categoryId: number | null): void {
    this.selectedCategory.set(categoryId);
    this.categoryChanged.emit(categoryId);
  }

  isSelected(categoryId: number | null): boolean {
    return this.selectedCategory() === categoryId;
  }
}
