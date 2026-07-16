import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MarketplaceStore } from '../../store/marketplace.store';

@Component({
  selector: 'app-service-catalog',
  templateUrl: './service-catalog.component.html',
  styleUrl: './service-catalog.component.css'
})
export class ServiceCatalogComponent implements OnInit {
  private readonly router = inject(Router);
  readonly store = inject(MarketplaceStore);

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.store.loadCategories();
    this.store.loadServices();
  }

  onSearch(keyword: string): void {
    this.store.search(keyword);
  }

  onCategoryChanged(categoryId: number | null): void {
    this.store.changeCategory(categoryId);
  }

  onSortChanged(sort: string): void {
    this.store.changeSort(sort);
  }

  openProviders(serviceId: number): void {
    this.router.navigate([
      '/marketplace/services',
      serviceId
    ]);
  }
}
