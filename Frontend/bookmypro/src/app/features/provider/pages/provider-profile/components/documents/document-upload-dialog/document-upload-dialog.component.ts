import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-document-upload-dialog',
  templateUrl: './document-upload-dialog.component.html',
  styleUrl: './document-upload-dialog.component.css'
})
export class DocumentUploadDialogComponent {
  form: FormGroup;
  selectedFile!: File;
  fileName = '';

  documentTypes = [

    'Aadhaar Card',

    'PAN Card',

    'Driving License',

    'Passport',

    'Trade License',

    'GST Certificate',

    'Police Verification',

    'Experience Certificate',

    'Other'

  ];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<DocumentUploadDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    this.form = this.fb.group({

      documentType: [
        '',
        Validators.required
      ],

      documentNumber: [
        '',
        Validators.required
      ],

      issueDate: [''],

      expiryDate: ['']

    });

  }

  selectFile(event: any): void {

    if (!event.target.files.length) {

      return;

    }

    this.selectedFile = event.target.files[0];

    this.fileName = this.selectedFile.name;

  }

  save(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    const formData = new FormData();

    formData.append(
      'document',
      this.selectedFile
    );

    Object.entries(this.form.value).forEach(

      ([key, value]) => {

        formData.append(
          key,
          value as string
        );

      }

    );

    this.dialogRef.close(formData);

  }

  close(): void {

    this.dialogRef.close();

  }

}
