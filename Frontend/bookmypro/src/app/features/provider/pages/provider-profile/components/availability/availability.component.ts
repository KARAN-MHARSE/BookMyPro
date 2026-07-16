import { Component, Input, OnInit, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ProviderProfileService } from '../../../../services/provider-profile.service';
import { AvailabilityResponse } from '../../../../model/provider-profile.model';

@Component({
  selector: 'app-availability',
  templateUrl: './availability.component.html',
  styleUrl: './availability.component.css',
})
export class AvailabilityComponent implements OnInit {
  @Input()
  credentialId = '';

  availabilityForm: FormGroup;

  weekDays = [
    { name: 'Monday', value: 'MONDAY' },
    { name: 'Tuesday', value: 'TUESDAY' },
    { name: 'Wednesday', value: 'WEDNESDAY' },
    { name: 'Thursday', value: 'THURSDAY' },
    { name: 'Friday', value: 'FRIDAY' },
    { name: 'Saturday', value: 'SATURDAY' },
    { name: 'Sunday', value: 'SUNDAY' },
  ];

  private fb = inject(FormBuilder);
  private profileService = inject(ProviderProfileService);

  constructor() {
    this.availabilityForm = this.fb.group({
      workingDays: this.fb.array(
        this.weekDays.map(() => this.fb.control(false))
      ),
      startTime: ['09:00'],
      endTime: ['18:00'],
      breakStart: ['13:00'],
      breakEnd: ['14:00'],
      serviceRadius: [15],
      maxBookings: [8],
      homeService: [true],
      emergencyService: [false],
      weekendAvailable: [false],
    });
  }

  ngOnInit(): void {
    if (this.credentialId) {
      this.profileService.getAvailabilityDetails(this.credentialId).subscribe({
        next: (res: AvailabilityResponse) => {
          if (res) {
            const mappedDays = this.weekDays.map(d => 
              res.workingDays ? res.workingDays.includes(d.value) : false
            );
            this.workingDaysControls.setValue(mappedDays);

            this.availabilityForm.patchValue({
              startTime: res.startTime,
              endTime: res.endTime,
              breakStart: res.breakStart,
              breakEnd: res.breakEnd,
              serviceRadius: res.serviceRadius,
              maxBookings: res.maxBookings,
              homeService: res.homeService,
              emergencyService: res.emergencyService,
              weekendAvailable: res.weekendAvailable
            });
          }
        },
        error: (err: any) => {
          console.error('Failed to load availability settings', err);
        }
      });
    }
  }

  get workingDaysControls(): FormArray {
    return this.availabilityForm.get('workingDays') as FormArray;
  }

  saveAvailability(): void {
    if (this.availabilityForm.invalid) {
      this.availabilityForm.markAllAsTouched();
      return;
    }

    const value = this.availabilityForm.value;
    const selectedDays = this.weekDays
      .filter((_, index) => value.workingDays[index])
      .map((day) => day.value);

    const request = {
      workingDays: selectedDays,
      startTime: value.startTime,
      endTime: value.endTime,
      breakStart: value.breakStart,
      breakEnd: value.breakEnd,
      serviceRadius: value.serviceRadius,
      maxBookings: value.maxBookings,
      homeService: value.homeService,
      emergencyService: value.emergencyService,
      weekendAvailable: value.weekendAvailable,
    };

    if (this.credentialId) {
      this.profileService.saveAvailabilityDetails(this.credentialId, request).subscribe({
        next: (res: AvailabilityResponse) => {
          alert('Availability settings saved successfully!');
        },
        error: (err: any) => {
          console.error('Failed to save availability settings', err);
          alert('Failed to save availability settings.');
        }
      });
    }
  }
}
