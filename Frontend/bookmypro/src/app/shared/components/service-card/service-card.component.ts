import { Component, Input } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-service-card',
  templateUrl: './service-card.component.html',
  styleUrl: './service-card.component.css'
})
export class ServiceCardComponent {
  @Input({ required: true }) title!: string;
  @Input() icon = '🧼';
  @Input() thumbGradient = 'linear-gradient(135deg,#DBEAFE,#EDE9FE)';
  @Input() badgeText?: string;
  @Input() badgeVariant = 'primary';
  @Input() rating = 0;
  @Input() reviewCount = 0;
  @Input() priceFrom = 0;
  @Input() routerLink: string | any[] = [];

  get starString(): string {
    const full = Math.round(this.rating);
    return '★'.repeat(full) + '☆'.repeat(Math.max(0, 5 - full));
  }

}
