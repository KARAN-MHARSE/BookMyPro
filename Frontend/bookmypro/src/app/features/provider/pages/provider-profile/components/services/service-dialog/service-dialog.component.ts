import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LookupOption, LookupResponse } from '@shared/models/lookup.model';
import { CategoryOption, ServiceOption, ServiceDialogData } from '../../../../../models/provider-profile.model';

@Component({
  selector: 'app-service-dialog',
  templateUrl: './service-dialog.component.html',
  styleUrl: './service-dialog.component.css'
})
export class ServiceDialogComponent implements OnInit {
  form: FormGroup;

  categories: CategoryOption[] = [];
  services: ServiceOption[] = [];
  availableServices: ServiceOption[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ServiceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ServiceDialogData
  ) {
    const serviceData = data?.service;
    const lookups = data?.lookups;

    this.categories = (lookups?.['SERVICE_CATEGORY'] ?? []).map((category) => ({
      id: category.id,
      name: category.name
    }));

    this.services = (lookups?.['SERVICE'] ?? []).map((service) => ({
      id: service.id,
      name: service.name,
      parentValue: service.parentValue
    }));

    let initialCategory = serviceData?.category || '';
    if (initialCategory && !this.isUuid(initialCategory)) {
      let searchKey = initialCategory;
      if (initialCategory.toLowerCase() === 'electrician') {
        searchKey = 'Electrical';
      }
      const match = this.categories.find(
        (category) => category.name.toLowerCase() === searchKey.toLowerCase()
      );
      if (match) {
        initialCategory = match.id;
      }
    }

    let initialService = serviceData?.serviceName || '';
    if (initialService && !this.isUuid(initialService)) {
      const match = this.services.find(
        (service) => service.name.toLowerCase() === initialService.toLowerCase()
      );
      if (match) {
        initialService = match.id;
      } else {
        const fallback = this.services.find((service) => service.parentValue === initialCategory);
        if (fallback) {
          initialService = fallback.id;
        }
      }
    }

    this.form = this.fb.group({
      serviceName: [initialService, Validators.required],
      category: [initialCategory, Validators.required],
      price: [serviceData?.price ?? '', Validators.required],
      duration: [serviceData?.duration ?? '', Validators.required],
      active: [serviceData?.active ?? true],
      experienceYears: [serviceData?.experienceYears ?? ''],
      priceType: [serviceData?.priceType || 'FIXED', Validators.required],
      homeService: [serviceData?.homeService ?? false],
      emergencyService: [serviceData?.emergencyService ?? false],
      weekendAvailable: [serviceData?.weekendAvailable ?? false],
      minimumPrice: [serviceData?.minimumPrice ?? ''],
      maximumPrice: [serviceData?.maximumPrice ?? '']
    });

    this.form.get('category')?.valueChanges.subscribe((categoryId: string) => {
      this.updateAvailableServices(categoryId);
      const currentService = this.form.get('serviceName')?.value;
      if (!this.availableServices.some((service) => service.id === currentService)) {
        this.form.get('serviceName')?.setValue('');
      }
    });
  }

  ngOnInit(): void {
    const activeCategoryId = this.form.get('category')?.value;
    if (activeCategoryId) {
      this.updateAvailableServices(activeCategoryId);
    }
  }

  private updateAvailableServices(categoryId: string): void {
    if (!categoryId) {
      this.availableServices = [];
      return;
    }

    this.availableServices = this.services.filter(
      (service) => service.parentValue === categoryId
    );
  }

  isUuid(value: string): boolean {
    const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i;
    return uuidRegex.test(value);
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.value;
    const category = this.categories.find((item) => item.id === value.category);
    const service = this.services.find((item) => item.id === value.serviceName);

    this.dialogRef.close({
      ...value,
      categoryName: category?.name ?? '',
      serviceRealName: service?.name ?? ''
    });
  }

  close(): void {
    this.dialogRef.close();
  }
}
