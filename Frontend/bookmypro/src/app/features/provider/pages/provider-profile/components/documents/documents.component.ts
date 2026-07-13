import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DocumentUploadDialogComponent } from './document-upload-dialog/document-upload-dialog.component';

interface ProviderDocument {
  documentId: number;
  documentType: string;
  documentNumber: string;
  status: string;
  uploadDate: string;
  issueDate?: string;
  expiryDate?: string;
}

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrl: './documents.component.css'
})
export class DocumentsComponent {

  documents: ProviderDocument[] = [
    {
      documentId: 1,
      documentType: 'Aadhaar Card',
      documentNumber: 'XXXX XXXX 4589',
      status: 'VERIFIED',
      uploadDate: '12 Jun 2026'
    },
    {
      documentId: 2,
      documentType: 'PAN Card',
      documentNumber: 'ABCDE1234F',
      status: 'PENDING',
      uploadDate: '15 Jun 2026'
    }
  ];

  constructor(private dialog: MatDialog) {}

  uploadDocument(): void {
    const dialogRef = this.dialog.open(DocumentUploadDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe((result: FormData) => {
      if (result) {
        const nextId = this.documents.length > 0 ? Math.max(...this.documents.map(d => d.documentId)) + 1 : 1;
        const documentType = result.get('documentType') as string;
        const documentNumber = result.get('documentNumber') as string;
        const issueDate = result.get('issueDate') as string;
        const expiryDate = result.get('expiryDate') as string;

        this.documents.push({
          documentId: nextId,
          documentType: documentType,
          documentNumber: documentNumber,
          issueDate: issueDate,
          expiryDate: expiryDate,
          status: 'PENDING',
          uploadDate: new Date().toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' })
        });
      }
    });
  }

  viewDocument(document: ProviderDocument): void {
    console.log(document);
  }

  editDocument(document: ProviderDocument): void {
    const dialogRef = this.dialog.open(DocumentUploadDialogComponent, {
      width: '700px',
      disableClose: true,
      data: document
    });

    dialogRef.afterClosed().subscribe((result: FormData) => {
      if (result) {
        const idx = this.documents.findIndex(d => d.documentId === document.documentId);
        if (idx !== -1) {
          const documentType = result.get('documentType') as string;
          const documentNumber = result.get('documentNumber') as string;
          const issueDate = result.get('issueDate') as string;
          const expiryDate = result.get('expiryDate') as string;

          this.documents[idx] = {
            ...document,
            documentType: documentType,
            documentNumber: documentNumber,
            issueDate: issueDate,
            expiryDate: expiryDate,
            status: 'PENDING'
          };
        }
      }
    });
  }

  deleteDocument(document: ProviderDocument): void {
    this.documents = this.documents.filter(
      x => x.documentId !== document.documentId
    );
  }
}

