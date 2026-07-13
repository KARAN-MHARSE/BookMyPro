import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-service-dialog',
  templateUrl: './service-dialog.component.html',
  styleUrl: './service-dialog.component.css'
})
export class ServiceDialogComponent {
  form: FormGroup;

  categories = [
    'Electrical',
    'Plumbing',
    'Cleaning',
    'Painting',
    'Appliance Repair'
  ];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ServiceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    this.form = this.fb.group({

      serviceName: [
        data?.serviceName || '',
        Validators.required
      ],

      category: [
        data?.category || '',
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
      ]

    });

  }

  save(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    this.dialogRef.close(this.form.value);

  }

  close(): void {

    this.dialogRef.close();

  }

}
