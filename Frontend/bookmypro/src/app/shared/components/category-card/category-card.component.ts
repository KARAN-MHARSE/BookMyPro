import { Component, Input } from '@angular/core';

export interface Category {
  name: string;
  icon: string;
  providers: number;
}

@Component({
  selector: 'app-category-card',
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.css',
})
export class CategoryCardComponent {
  @Input({ required: true }) category!: Category;
}
