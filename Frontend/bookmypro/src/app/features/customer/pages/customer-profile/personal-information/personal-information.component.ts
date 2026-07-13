import { Component, computed, inject, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { LookUpStore } from '@features/customer/store/look-up.store';

@Component({
  selector: 'app-personal-information',
  templateUrl: './personal-information.component.html',
  styleUrl: './personal-information.component.css'
})
export class PersonalInformationComponent {
  @Input({ required: true })
  group!: FormGroup;

  @Input() imagePreview: string | ArrayBuffer | null = null;

  readonly lookUpStore = inject(LookUpStore);

  readonly languages = computed(() => {
    const lookups = this.lookUpStore.lookups();
    return (lookups['LANGUAGE'] ?? []).map((item) => ({
      label: item.name,
      value: item.name,
    }));
  });

  genders = ['MALE','FEMALE','OTHER'];

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      return;
    }

    const file = input.files[0];

    this.group.patchValue({
      profilePhoto: file
    });

    const reader = new FileReader();

    reader.onload = () => {
      this.imagePreview = reader.result;
    };

    reader.readAsDataURL(file);
  }

  removeImage(): void {
    this.imagePreview = null;
    this.group.patchValue({
      profilePhoto: null
    });
  }
}
