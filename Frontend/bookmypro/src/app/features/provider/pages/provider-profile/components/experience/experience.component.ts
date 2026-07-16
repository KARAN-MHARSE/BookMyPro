import { Component, Input, OnInit, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ExperienceDialogComponent } from './experience-dialog/experience-dialog.component';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { ExperienceResponse } from '../../../../model/provider-profile.model';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrl: './experience.component.css'
})
export class ExperienceComponent implements OnInit {
  @Input()
  credentialId = '';

  experiences: any[] = [];

  private dialog = inject(MatDialog);
  private profileService = inject(ProviderProfileService);

  ngOnInit(): void {
    this.loadExperiences();
  }

  loadExperiences(): void {
    if (this.credentialId) {
      this.profileService.getExperiences(this.credentialId).subscribe({
        next: (res: ExperienceResponse[]) => {
          this.experiences = res.map(e => ({
            ...e,
            company: e.companyName,
            current: e.currentlyWorking
          }));
        },
        error: (err: any) => {
          console.error('Failed to load experiences list', err);
        }
      });
    }
  }

  addExperience(): void {
    const dialogRef = this.dialog.open(ExperienceDialogComponent, {
      width: '700px',
      disableClose: true,
      data: null
    });

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        const payload = {
          credentialId: this.credentialId,
          companyName: result.company,
          designation: result.designation,
          startDate: result.startDate,
          endDate: result.current ? null : result.endDate,
          currentlyWorking: result.current,
          description: result.description
        };

        this.profileService.saveExperience(payload).subscribe({
          next: () => {
            this.loadExperiences();
          },
          error: (err: any) => {
            console.error('Failed to add experience record', err);
            alert('Failed to add experience.');
          }
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

    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        const payload = {
          experienceId: experience.experienceId,
          credentialId: this.credentialId,
          companyName: result.company,
          designation: result.designation,
          startDate: result.startDate,
          endDate: result.current ? null : result.endDate,
          currentlyWorking: result.current,
          description: result.description
        };

        this.profileService.saveExperience(payload).subscribe({
          next: () => {
            this.loadExperiences();
          },
          error: (err: any) => {
            console.error('Failed to update experience record', err);
            alert('Failed to update experience.');
          }
        });
      }
    });
  }

  deleteExperience(experience: any): void {
    if (experience.experienceId && confirm('Are you sure you want to delete this experience record?')) {
      this.profileService.deleteExperience(experience.experienceId).subscribe({
        next: () => {
          this.loadExperiences();
        },
        error: (err: any) => {
          console.error('Failed to delete experience record', err);
          alert('Failed to delete experience.');
        }
      });
    }
  }
}
