import { Component, Input } from '@angular/core';
import { Faq } from '@app/features/services/models/service.model';

@Component({
  selector: 'app-service-faq',
  templateUrl: './service-faq.component.html',
  styleUrl: './service-faq.component.css'
})
export class ServiceFaqComponent {
  @Input({ required: true })
  faqs: Faq[] = [];
}
