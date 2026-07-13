import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-education-dialog',
  templateUrl: './education-dialog.component.html',
  styleUrl: './education-dialog.component.css'
})
export class EducationDialogComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EducationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    this.form = this.fb.group({

      institution: [
        data?.institution || '',
        Validators.required
      ],

      degree: [
        data?.degree || '',
        Validators.required
      ],

      specialization: [
        data?.specialization || ''
      ],

      startYear: [
        data?.startYear || (data?.year && data.year !== 'Present' ? Number(data.year) - 3 : ''),
        Validators.required
      ],

      endYear: [
        data?.endYear || (data?.year && data.year !== 'Present' ? data.year : '')
      ],

      currentlyStudying: [
        data?.currentlyStudying || (data?.year === 'Present') || false
      ],

      description: [
        data?.description || ''
      ]

    });

    this.form.get('currentlyStudying')?.valueChanges.subscribe((studying: boolean) => {
      const endYearControl = this.form.get('endYear');
      if (studying) {
        endYearControl?.setValue('');
        endYearControl?.disable();
      } else {
        endYearControl?.enable();
      }
    });

    if (data?.year === 'Present' || data?.currentlyStudying) {
      this.form.get('endYear')?.disable();
    }
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const val = this.form.getRawValue();
    const result = {
      institution: val.institution,
      degree: val.degree,
      specialization: val.specialization,
      startYear: val.startYear,
      endYear: val.currentlyStudying ? '' : val.endYear,
      currentlyStudying: val.currentlyStudying,
      year: val.currentlyStudying ? 'Present' : val.endYear,
      description: val.description
    };
    this.dialogRef.close(result);
  }

  close(): void {
    this.dialogRef.close();
  }
}
