import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-service-dialog',
  templateUrl: './service-dialog.component.html',
  styleUrl: './service-dialog.component.css'
})
export class ServiceDialogComponent implements OnInit {
  form: FormGroup;

  categories = [
    { id: '11111111-1111-1111-1111-111111111111', name: 'Electrical' },
    { id: '22222222-2222-2222-2222-222222222222', name: 'Plumbing' },
    { id: '33333333-3333-3333-3333-333333333333', name: 'Cleaning' },
    { id: '44444444-4444-4444-4444-444444444444', name: 'Painting' },
    { id: '55555555-5555-5555-5555-555555555555', name: 'Appliance Repair' }
  ];

  services = [
    // Electrical (11111111-...)
    { id: 'a1111111-1111-1111-1111-111111111111', categoryId: '11111111-1111-1111-1111-111111111111', name: 'Repair Fan' },
    { id: 'a1111112-1111-1111-1111-111111111111', categoryId: '11111111-1111-1111-1111-111111111111', name: 'Install Light/Bulb' },
    { id: 'a1111113-1111-1111-1111-111111111111', categoryId: '11111111-1111-1111-1111-111111111111', name: 'Wiring/Rewiring' },
    { id: 'a1111114-1111-1111-1111-111111111111', categoryId: '11111111-1111-1111-1111-111111111111', name: 'Switchboard installation' },
    { id: 'a1111115-1111-1111-1111-111111111111', categoryId: '11111111-1111-1111-1111-111111111111', name: 'Geyser Repair' },
    { id: 'a1111116-1111-1111-1111-111111111111', categoryId: '11111111-1111-1111-1111-111111111111', name: 'AC Servicing' },

    // Plumbing (22222222-...)
    { id: 'b2222221-2222-2222-2222-222222222222', categoryId: '22222222-2222-2222-2222-222222222222', name: 'Tap Repair/Replacement' },
    { id: 'b2222222-2222-2222-2222-222222222222', categoryId: '22222222-2222-2222-2222-222222222222', name: 'Drain Unclogging' },
    { id: 'b2222223-2222-2222-2222-222222222222', categoryId: '22222222-2222-2222-2222-222222222222', name: 'Toilet Repair' },
    { id: 'b2222224-2222-2222-2222-222222222222', categoryId: '22222222-2222-2222-2222-222222222222', name: 'Water Leakage Repair' },
    { id: 'b2222225-2222-2222-2222-222222222222', categoryId: '22222222-2222-2222-2222-222222222222', name: 'Water Tank Cleaning' },

    // Cleaning (33333333-...)
    { id: 'c3333331-3333-3333-3333-333333333333', categoryId: '33333333-3333-3333-3333-333333333333', name: 'Deep House Cleaning' },
    { id: 'c3333332-3333-3333-3333-333333333333', categoryId: '33333333-3333-3333-3333-333333333333', name: 'Bathroom Cleaning' },
    { id: 'c3333333-3333-3333-3333-333333333333', categoryId: '33333333-3333-3333-3333-333333333333', name: 'Sofa/Carpet Cleaning' },
    { id: 'c3333334-3333-3333-3333-333333333333', categoryId: '33333333-3333-3333-3333-333333333333', name: 'Kitchen Deep Cleaning' },

    // Painting (44444444-...)
    { id: 'd4444441-4444-4444-4444-444444444444', categoryId: '44444444-4444-4444-4444-444444444444', name: 'Full Home Painting' },
    { id: 'd4444442-4444-4444-4444-444444444444', categoryId: '44444444-4444-4444-4444-444444444444', name: 'Wall Stencil work' },
    { id: 'd4444443-4444-4444-4444-444444444444', categoryId: '44444444-4444-4444-4444-444444444444', name: 'Waterproofing painting' },
    { id: 'd4444444-4444-4444-4444-444444444444', categoryId: '44444444-4444-4444-4444-444444444444', name: 'Single Wall Painting' },

    // Appliance Repair (55555555-...)
    { id: 'e5555551-5555-5555-5555-555555555555', categoryId: '55555555-5555-5555-5555-555555555555', name: 'Refrigerator Repair' },
    { id: 'e5555552-5555-5555-5555-555555555555', categoryId: '55555555-5555-5555-5555-555555555555', name: 'Washing Machine Repair' },
    { id: 'e5555553-5555-5555-5555-555555555555', categoryId: '55555555-5555-5555-5555-555555555555', name: 'Microwave Oven Repair' },
    { id: 'e5555554-5555-5555-5555-555555555555', categoryId: '55555555-5555-5555-5555-555555555555', name: 'Television Repair/Mounting' }
  ];

  availableServices: any[] = [];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ServiceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    let initialCategory = data?.category || '';
    if (initialCategory && !this.isUuid(initialCategory)) {
      let searchKey = initialCategory;
      if (initialCategory.toLowerCase() === 'electrician') {
        searchKey = 'Electrical';
      }
      const match = this.categories.find(c => c.name.toLowerCase() === searchKey.toLowerCase());
      if (match) {
        initialCategory = match.id;
      }
    }

    let initialService = data?.serviceName || '';
    if (initialService && !this.isUuid(initialService)) {
      const match = this.services.find(s => s.name.toLowerCase() === initialService.toLowerCase());
      if (match) {
        initialService = match.id;
      } else {
        const fallback = this.services.find(s => s.categoryId === initialCategory);
        if (fallback) {
          initialService = fallback.id;
        }
      }
    }

    this.form = this.fb.group({
      serviceName: [
        initialService,
        Validators.required
      ],
      category: [
        initialCategory,
        Validators.required
      ],
      price: [
        data?.price || '',
        Validators.required
      ],
      duration: [
        data?.duration || '',
        Validators.required
      ],
      active: [
        data?.active ?? true
      ],
      experienceYears: [
        data?.experienceYears || ''
      ],
      priceType: [
        data?.priceType || 'FIXED',
        Validators.required
      ],
      homeService: [
        data?.homeService ?? false
      ],
      emergencyService: [
        data?.emergencyService ?? false
      ],
      weekendAvailable: [
        data?.weekendAvailable ?? false
      ],
      minimumPrice: [
        data?.minimumPrice || ''
      ],
      maximumPrice: [
        data?.maximumPrice || ''
      ]
    });

    this.form.get('category')?.valueChanges.subscribe((catId: string) => {
      this.availableServices = this.services.filter(s => s.categoryId === catId);
      const currentService = this.form.get('serviceName')?.value;
      if (!this.availableServices.some(s => s.id === currentService)) {
        this.form.get('serviceName')?.setValue('');
      }
    });
  }

  ngOnInit(): void {
    const activeCatId = this.form.get('category')?.value;
    if (activeCatId) {
      this.availableServices = this.services.filter(s => s.categoryId === activeCatId);
    }
  }

  isUuid(str: string): boolean {
    const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i;
    return uuidRegex.test(str);
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const val = this.form.value;
    const catObj = this.categories.find(c => c.id === val.category);
    const svcObj = this.services.find(s => s.id === val.serviceName);

    this.dialogRef.close({
      ...val,
      categoryName: catObj ? catObj.name : '',
      serviceRealName: svcObj ? svcObj.name : ''
    });
  }

  close(): void {
    this.dialogRef.close();
  }
}
