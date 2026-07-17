import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-service-includes',
  templateUrl: './service-includes.component.html',
  styleUrl: './service-includes.component.css'
})
export class ServiceIncludesComponent {
  @Input({ required: true })
  items: string[] = [];
}
