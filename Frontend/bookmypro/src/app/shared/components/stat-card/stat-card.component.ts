import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-stat-card',
  templateUrl: './stat-card.component.html',
  styleUrl: './stat-card.component.css'
})
export class StatCardComponent {
 @Input({ required: true }) label!: string;
  @Input({ required: true }) value!: string;
  @Input() deltaLabel?: string;
  @Input() deltaDirection = 'neutral';
}
