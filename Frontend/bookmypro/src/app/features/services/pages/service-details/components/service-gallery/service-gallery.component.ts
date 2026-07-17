import { Component, Input } from '@angular/core';
import { GalleryImage } from '@app/features/services/models/service.model';

@Component({
  selector: 'app-service-gallery',
  templateUrl: './service-gallery.component.html',
  styleUrl: './service-gallery.component.css'
})
export class ServiceGalleryComponent {
  @Input({ required: true })
  images: GalleryImage[] = [];
}
