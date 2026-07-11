import { Component, inject } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'bookmypro';
  isAuthPage=false;

   router = inject(Router);

  ngOnInit(): void {
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd)
      )
      .subscribe(event => {
        const url = event.urlAfterRedirects;
        this.isAuthPage = url.includes("auth");
        console.log('Is Auth Page:', this.isAuthPage);

        
      });
  }
  
}
