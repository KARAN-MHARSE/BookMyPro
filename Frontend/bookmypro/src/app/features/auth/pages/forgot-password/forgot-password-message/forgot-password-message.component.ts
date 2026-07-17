import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-forgot-password-message',
  templateUrl: './forgot-password-message.component.html',
  styleUrl: './forgot-password-message.component.css'
})
export class ForgotPasswordMessageComponent {
 @Output()
  continueClicked = new EventEmitter<void>();

  continueToLogin() {

    this.continueClicked.emit();

  }
}
