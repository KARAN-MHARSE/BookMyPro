import { Component, Input, OnInit, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EducationDialogComponent } from './education-dialog/education-dialog.component';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { EducationResponse } from '../../../../models/provider-profile.model';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrl: './education.component.css'
})
export class EducationComponent implements OnInit {
  @Input()
  credentialId = '';

  educations: any[] = [];

  private dialog = inject(MatDialog);
  private profileService = inject(ProviderProfileService);

  ngOnInit(): void {
    this.loadEducations();
  }

  loadEducations(): void {
    if (this.credentialId) {
      this.profileService.getEducations(this.credentialId).subscribe({
        next: (res: EducationResponse[]) => {
          this.educations = res.map(e => ({
            ...e,
            institution: e.institutionName,
            year: e.currentlyStudying ? 'Present' : e.endYear
          }));
        },
        error: (err: any) => {
          console.error('Failed to load educations list', err);
        }
      });
    }
  }

  addEducation(): void {
    const dialogRef = this.dialog.open(EducationDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        const payload = {
          credentialId: this.credentialId,
          institutionName: result.institution,
          degree: result.degree,
          specialization: result.specialization,
          startYear: String(result.startYear),
          endYear: String(result.endYear),
          currentlyStudying: result.currentlyStudying,
          description: result.description
        };

        this.profileService.saveEducation(payload).subscribe({
          next: () => {
            this.loadEducations();
          },
          error: (err: any) => {
            console.error('Failed to add education record', err);
            alert('Failed to add qualification.');
          }
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

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        const payload = {
          educationId: education.educationId,
          credentialId: this.credentialId,
          institutionName: result.institution,
          degree: result.degree,
          specialization: result.specialization,
          startYear: String(result.startYear),
          endYear: String(result.endYear),
          currentlyStudying: result.currentlyStudying,
          description: result.description
        };

        this.profileService.saveEducation(payload).subscribe({
          next: () => {
            this.loadEducations();
          },
          error: (err: any) => {
            console.error('Failed to update education record', err);
            alert('Failed to update qualification.');
          }
        });
      }
    });
  }

  deleteEducation(education: any): void {
    if (education.educationId && confirm('Are you sure you want to delete this qualification?')) {
      this.profileService.deleteEducation(education.educationId).subscribe({
        next: () => {
          this.loadEducations();
        },
        error: (err: any) => {
          console.error('Failed to delete education record', err);
          alert('Failed to delete qualification.');
        }
      });
    }
  }
}
