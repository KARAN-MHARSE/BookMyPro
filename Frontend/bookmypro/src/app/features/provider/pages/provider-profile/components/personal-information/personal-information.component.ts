import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-personal-information',
  templateUrl: './personal-information.component.html',
  styleUrl: './personal-information.component.css'
})
export class PersonalInformationComponent {
@Input()
  group!: FormGroup;

  imagePreview: string | ArrayBuffer | null = null;

  genders = [
    'Male',
    'Female',
    'Other'
  ];

  languages = [
    'English',
    'Hindi',
    'Marathi'
  ];

  selectImage(event: Event): void {

    const file = (event.target as HTMLInputElement)
      .files?.[0];

    if (!file) {
      return;
    }

    this.group.patchValue({
      profilePhoto: file
    });

    const reader = new FileReader();

    reader.onload = () => {

      this.imagePreview = reader.result;

    };

    reader.readAsDataURL(file);

  }
}
