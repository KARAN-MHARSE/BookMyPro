import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-stepper-card',
  templateUrl: './stepper-card.component.html',
  styleUrl: './stepper-card.component.css'
})
export class StepperCardComponent {
  @Output() saveEvent = new EventEmitter<any>();
  @Output() cancelEvent = new EventEmitter<any>();

  save() {
    this.saveEvent.emit();
  }

  cancel() {
    this.cancelEvent.emit();
  }
}
