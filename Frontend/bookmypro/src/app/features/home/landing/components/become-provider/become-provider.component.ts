import { Component } from '@angular/core';

@Component({
  selector: 'app-become-provider',
  templateUrl: './become-provider.component.html',
  styleUrl: './become-provider.component.css'
})
export class BecomeProviderComponent {

  apply(): void {
    console.log('Apply as provider');
  }

  learnMore(): void {
    console.log('Learn more');
  }
}
