import { Injectable, computed, signal } from '@angular/core';
import { ServiceSummary, MarketplaceCategory } from '../models/service.model';

@Injectable({
  providedIn: 'root'
})
export class MarketplaceStore {
  private readonly _loading = signal(false);
  private readonly _categories = signal<MarketplaceCategory[]>([]);
  private readonly _services = signal<ServiceSummary[]>([]);
  private readonly _searchKeyword = signal('');
  private readonly _selectedCategoryId = signal<number | null>(null);
  private readonly _sort = signal('recommended');

  readonly loading = this._loading.asReadonly();
  readonly categories = this._categories.asReadonly();
  readonly sort = this._sort.asReadonly();

  readonly filteredServices = computed(() => {
    let list = [...this._services()];
    const categoryId = this._selectedCategoryId();
    const keyword = this._searchKeyword().toLowerCase().trim();
    const sortVal = this._sort();

    // Filter by Category
    if (categoryId !== null) {
      const cat = this._categories().find(c => c.id === categoryId);
      if (cat) {
        list = list.filter(s => s.categoryName.toLowerCase() === cat.name.toLowerCase());
      }
    }

    // Filter by Search Keyword
    if (keyword) {
      list = list.filter(s =>
        s.name.toLowerCase().includes(keyword) ||
        s.categoryName.toLowerCase().includes(keyword)
      );
    }

    // Sort
    if (sortVal === 'rating') {
      list.sort((a, b) => b.averageRating - a.averageRating);
    } else if (sortVal === 'priceAsc') {
      list.sort((a, b) => a.startingPrice - b.startingPrice);
    } else if (sortVal === 'priceDesc') {
      list.sort((a, b) => b.startingPrice - a.startingPrice);
    } else if (sortVal === 'providers') {
      list.sort((a, b) => b.providerCount - a.providerCount);
    }

    return list;
  });

  loadCategories(): void {
    this._categories.set([
      { id: 1, name: 'Cleaning', iconUrl: '' },
      { id: 2, name: 'Repairs', iconUrl: '' },
      { id: 3, name: 'Beauty & Wellness', iconUrl: '' },
      { id: 4, name: 'Appliances', iconUrl: '' },
      { id: 5, name: 'Painting', iconUrl: '' },
      { id: 6, name: 'Pest Control', iconUrl: '' },
      { id: 7, name: 'Moving', iconUrl: '' }
    ]);
  }

  loadServices(): void {
    this._loading.set(true);
    // Mocking async delay
    setTimeout(() => {
      this._services.set([
        {
          id: 1,
          name: 'Deep Home Cleaning',
          slug: 'deep-home-cleaning',
          categoryName: 'Cleaning',
          thumbnailUrl: 'https://images.unsplash.com/photo-1581578731548-c64695cc6952?auto=format&fit=crop&w=400&q=80',
          averageRating: 4.8,
          totalReviews: 2100,
          providerCount: 1200,
          startingPrice: 599,
          estimatedDuration: 60
        },
        {
          id: 2,
          name: 'Bathroom Deep Clean',
          slug: 'bathroom-deep-clean',
          categoryName: 'Cleaning',
          thumbnailUrl: 'https://images.unsplash.com/photo-1584824486509-112e4181ff6b?auto=format&fit=crop&w=400&q=80',
          averageRating: 4.8,
          totalReviews: 1300,
          providerCount: 640,
          startingPrice: 349,
          estimatedDuration: 45
        },
        {
          id: 3,
          name: 'Faucet & Plumbing Repair',
          slug: 'faucet-repair',
          categoryName: 'Repairs',
          thumbnailUrl: 'https://images.unsplash.com/photo-1504328345606-18bbc8c9d7d1?auto=format&fit=crop&w=400&q=80',
          averageRating: 4.7,
          totalReviews: 860,
          providerCount: 820,
          startingPrice: 249,
          estimatedDuration: 30
        },
        {
          id: 4,
          name: 'Salon at Home',
          slug: 'salon-at-home',
          categoryName: 'Beauty & Wellness',
          thumbnailUrl: 'https://images.unsplash.com/photo-1560066984-138dadb4c035?auto=format&fit=crop&w=400&q=80',
          averageRating: 4.9,
          totalReviews: 540,
          providerCount: 410,
          startingPrice: 299,
          estimatedDuration: 45
        }
      ]);
      this._loading.set(false);
    }, 150);
  }

  search(keyword: string): void {
    this._searchKeyword.set(keyword);
  }

  changeCategory(categoryId: number | null): void {
    this._selectedCategoryId.set(categoryId);
  }

  changeSort(sort: string): void {
    this._sort.set(sort);
  }
}
