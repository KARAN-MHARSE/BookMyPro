import { Component } from '@angular/core';

@Component({
  selector: 'app-hero',
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.css'
})
export class HeroComponent {
  service = '';

  location = '';

  search(): void {
    console.log({
      service: this.service,
      location: this.location
    });

    // TODO
    // Navigate to service listing page
    // or emit event to parent component
  }
}
