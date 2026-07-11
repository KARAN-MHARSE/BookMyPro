import { Component } from '@angular/core';

@Component({
  selector: 'app-how-it-works',
  templateUrl: './how-it-works.component.html',
  styleUrl: './how-it-works.component.css'
})
export class HowItWorksComponent {
  steps = [
    {
      number: 1,
      title: 'Choose a Service',
      description: 'Browse thousands of verified professionals across multiple service categories.'
    },
    {
      number: 2,
      title: 'Book a Time',
      description: 'Select your preferred date and time that fits your schedule.'
    },
    {
      number: 3,
      title: 'Get It Done',
      description: 'Sit back while an experienced professional completes your service.'
    }
  ];
}
