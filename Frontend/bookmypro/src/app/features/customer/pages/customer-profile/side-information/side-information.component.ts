import { Component } from '@angular/core';

@Component({
  selector: 'app-side-information',
  templateUrl: './side-information.component.html',
  styleUrl: './side-information.component.css'
})
export class SideInformationComponent {
  completionPercentage = 70;
  completedFields = 7;
  totalFields = 10;

  tips = [
    {
      icon: '🛡️',
      text: 'Your data is encrypted and never sold. See our privacy policy.'
    },
    {
      icon: '📍',
      text: 'Location helps us match you with vetted pros nearby.'
    },
    {
      icon: '🎂',
      text: 'Date of birth is used to verify age-restricted services.'
    },
    {
      icon: '🌐',
      text: 'Preferred language customizes chat and receipts.'
    }
  ];
}
