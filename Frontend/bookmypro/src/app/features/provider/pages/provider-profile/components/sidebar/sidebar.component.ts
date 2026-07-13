import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent {
  @Input() activeSection = 'personal';
  @Input() completion = 35;
  @Output() sectionChange = new EventEmitter<string>();

  menuItems = [
    { id: 'personal', title: 'Personal Information', icon: '👤' },
    { id: 'professional', title: 'Professional Information', icon: '💼' },
    { id: 'education', title: 'Education', icon: '🎓' },
    { id: 'experience', title: 'Experience', icon: '🏢' },
    { id: 'documents', title: 'Documents', icon: '📄' },
    { id: 'services', title: 'Services', icon: '🛠️' },
    { id: 'availability', title: 'Availability', icon: '📅' },
    { id: 'bank', title: 'Bank Details', icon: '🏦' }
  ];

  selectSection(id: string): void {
    this.sectionChange.emit(id);
  }
}
