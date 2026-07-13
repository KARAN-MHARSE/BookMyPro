import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ExperienceDialogComponent } from './experience-dialog/experience-dialog.component';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrl: './experience.component.css'
})
export class ExperienceComponent {

  experiences: any[] = [
    {
      experienceId: 1,
      company: 'Urban Company',
      designation: 'Senior Electrician',
      startDate: '2021-01-01',
      endDate: '2024-02-28',
      current: false
    },
    {
      experienceId: 2,
      company: 'BookMyPro',
      designation: 'Independent Professional',
      startDate: '2024-03-01',
      endDate: '',
      current: true
    }
  ];

  constructor(private dialog: MatDialog) {}

  addExperience(): void {
    const dialogRef = this.dialog.open(ExperienceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const nextId = this.experiences.length > 0 ? Math.max(...this.experiences.map(e => e.experienceId)) + 1 : 1;
        this.experiences.push({
          experienceId: nextId,
          ...result
        });
      }
    });
  }

  editExperience(experience: any): void {
    const dialogRef = this.dialog.open(ExperienceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: experience
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const idx = this.experiences.findIndex(e => e.experienceId === experience.experienceId);
        if (idx !== -1) {
          this.experiences[idx] = {
            ...experience,
            ...result
          };
        }
      }
    });
  }

  deleteExperience(experience: any): void {
    this.experiences = this.experiences.filter(
      x => x.experienceId !== experience.experienceId
    );
  }
}

