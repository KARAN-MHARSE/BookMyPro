import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-provider-card',
  templateUrl: './provider-card.component.html',
  styleUrl: './provider-card.component.css'
})
export class ProviderCardComponent {
  @Input({ required: true }) name!: string;
  @Input({ required: true }) initials!: string;
  @Input() avatarGradient = 'linear-gradient(135deg,#818CF8,#6366F1)';
  @Input() category = '';
  @Input() location = '';
  @Input() rating = 0;
  @Input() value?: string;
}
