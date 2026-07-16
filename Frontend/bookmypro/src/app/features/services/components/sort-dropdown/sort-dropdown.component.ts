import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SortOption } from '../../models/service.model';

@Component({
  selector: 'app-sort-dropdown',
  templateUrl: './sort-dropdown.component.html',
  styleUrl: './sort-dropdown.component.css'
})
export class SortDropdownComponent {
  @Input()
  options: SortOption[] = [
    {
      value: 'recommended',
      label: 'Recommended'
    },
    {
      value: 'rating',
      label: 'Highest Rated'
    },
    {
      value: 'priceAsc',
      label: 'Price: Low to High'
    },
    {
      value: 'priceDesc',
      label: 'Price: High to Low'
    },
    {
      value: 'providers',
      label: 'Most Professionals'
    }
  ];

  @Input()
  selected = 'recommended';

  @Output()
  readonly selectionChanged =
    new EventEmitter<string>();

  onSelectionChange(value: string): void {

    this.selectionChanged.emit(value);

  }
}
