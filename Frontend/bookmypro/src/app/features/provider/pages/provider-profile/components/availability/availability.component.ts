import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-availability',
  templateUrl: './availability.component.html',
  styleUrl: './availability.component.css',
})
export class AvailabilityComponent {
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

  constructor(private fb: FormBuilder) {
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

  get workingDaysControls(): FormArray {
    return this.availabilityForm.get('workingDays') as FormArray;
  }

  saveAvailability(): void {
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

    console.log(request);
    // call API here
  }
}
