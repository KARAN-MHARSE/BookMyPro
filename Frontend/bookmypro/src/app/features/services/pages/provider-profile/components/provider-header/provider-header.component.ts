import { Component, Input } from '@angular/core';
import { ProviderDetails } from '../../provider-profile.component';

@Component({
  selector: 'app-provider-header',
  templateUrl: './provider-header.component.html',
  styleUrl: './provider-header.component.css'
})
export class ProviderHeaderComponent {
  @Input({ required: true }) provider!: ProviderDetails;
}
