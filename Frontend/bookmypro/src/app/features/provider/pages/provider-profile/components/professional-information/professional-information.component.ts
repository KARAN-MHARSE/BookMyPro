import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-professional-information',
  templateUrl: './professional-information.component.html',
  styleUrl: './professional-information.component.css'
})
export class ProfessionalInformationComponent {
  @Input()
  group!: FormGroup;

  providerTypes = [

    'Electrician',

    'Plumber',

    'Carpenter',

    'Painter',

    'Cleaner',

    'Mechanic',

    'AC Technician',

    'Appliance Repair',

    'Salon',

    'Tutor',

    'Fitness Trainer'

  ];


}
