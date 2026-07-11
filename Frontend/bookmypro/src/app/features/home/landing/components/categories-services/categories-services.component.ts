import { Component } from '@angular/core';
import { Category } from '../../../../../shared/components/category-card/category-card.component';

@Component({
  selector: 'app-categories-services',
  templateUrl: './categories-services.component.html',
  styleUrl: './categories-services.component.css'
})
export class CategoriesServicesComponent {

  categories: Category[] = [
    { name: 'Cleaning', icon: '🧹', providers: 1240 },
    { name: 'Plumbing', icon: '🔧', providers: 830 },
    { name: 'Electrical', icon: '⚡', providers: 640 },
    { name: 'Beauty', icon: '💄', providers: 2100 },
    { name: 'Gardening', icon: '🪴', providers: 410 },
    { name: 'Painting', icon: '🎨', providers: 520 }
  ];
}
