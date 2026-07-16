import { Component, DestroyRef, EventEmitter, inject, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-service-search',
  templateUrl: './service-search.component.html',
  styleUrl: './service-search.component.css'
})
export class ServiceSearchComponent {
  private readonly destroyRef = inject(DestroyRef);

  @Output()
  readonly search = new EventEmitter<string>();

  readonly searchControl =
    new FormControl('', { nonNullable: true });

  constructor() {

    this.searchControl.valueChanges
      .pipe(
        debounceTime(400),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(value => {

        this.search.emit(value.trim());

      });

  }

  clear(): void {

    this.searchControl.setValue('');

  }
}
