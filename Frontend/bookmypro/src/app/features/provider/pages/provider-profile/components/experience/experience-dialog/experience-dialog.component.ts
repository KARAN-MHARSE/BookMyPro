import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-experience-dialog',
  templateUrl: './experience-dialog.component.html',
  styleUrl: './experience-dialog.component.css'
})
export class ExperienceDialogComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ExperienceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    this.form = this.fb.group({

      companyName: [
        data?.company || '',
        Validators.required
      ],

      designation: [
        data?.designation || '',
        Validators.required
      ],

      startDate: [
        data?.startDate || '',
        Validators.required
      ],

      endDate: [
        data?.endDate || ''
      ],

      currentlyWorking: [
        data?.current || false
      ],

      description: [
        data?.description || ''
      ]

    });

    // Handle end date state based on currentlyWorking checkbox status
    this.form.get('currentlyWorking')?.valueChanges.subscribe((current: boolean) => {
      const endDateControl = this.form.get('endDate');
      if (current) {
        endDateControl?.setValue('');
        endDateControl?.disable();
      } else {
        endDateControl?.enable();
      }
    });

    // If initial data indicates currentlyWorking is true, disable end date
    if (data?.current) {
      this.form.get('endDate')?.disable();
    }
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const val = this.form.getRawValue();
    const result = {
      company: val.companyName,
      designation: val.designation,
      startDate: val.startDate,
      endDate: val.currentlyWorking ? '' : val.endDate,
      current: val.currentlyWorking,
      description: val.description
    };
    this.dialogRef.close(result);
  }

  close(): void {
    this.dialogRef.close();
  }
}
