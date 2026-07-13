import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EducationDialogComponent } from './education-dialog/education-dialog.component';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrl: './education.component.css'
})
export class EducationComponent {

  educations: any[] = [
    {
      educationId: 1,
      degree: 'B.Tech Computer Engineering',
      institution: 'Pune University',
      year: 2023
    }
  ];

  constructor(private dialog: MatDialog) {}

  addEducation(): void {
    const dialogRef = this.dialog.open(EducationDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const nextId = this.educations.length > 0 ? Math.max(...this.educations.map(e => e.educationId)) + 1 : 1;
        this.educations.push({
          educationId: nextId,
          ...result
        });
      }
    });
  }

  editEducation(education: any): void {
    const dialogRef = this.dialog.open(EducationDialogComponent, {
      width: '700px',
      disableClose: true,
      data: education
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const idx = this.educations.findIndex(e => e.educationId === education.educationId);
        if (idx !== -1) {
          this.educations[idx] = {
            ...education,
            ...result
          };
        }
      }
    });
  }

  deleteEducation(education: any): void {
    this.educations = this.educations.filter(
      x => x.educationId !== education.educationId
    );
  }
}
