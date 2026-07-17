import { Injectable, computed, inject, signal } from '@angular/core';
import { ServiceSummary, MarketplaceCategory } from '../models/service.model';
import { MarketplaceService } from '../services/marketplace.service';

@Injectable({
  providedIn: 'root'
})
export class MarketplaceStore {
  private readonly marketplaceService = inject(MarketplaceService);
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
        list = list.filter(s => s.serviceCategoryName.toLowerCase() === cat.name.toLowerCase());
      }
    }

    // Filter by Search Keyword
    if (keyword) {
      list = list.filter(s =>
        s.serviceName.toLowerCase().includes(keyword) ||
        s.serviceCategoryName.toLowerCase().includes(keyword)
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
    this.marketplaceService.getServicesGridList().subscribe({
      next: (services) => {
        this._services.set(services);
        this._loading.set(false);
      },
      error: () => {
        this._loading.set(false);
      }
    });
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
