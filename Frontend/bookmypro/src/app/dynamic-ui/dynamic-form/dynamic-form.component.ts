import { Component, DestroyRef, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { DynammicField } from '../dynamic-ui.model';
import { DynamicFormService } from '../dynamic-form.service';
import { FormGroup } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrl: './dynamic-form.component.css'
})
export class DynamicFormComponent implements OnInit,OnDestroy {
  @Input() fields!: DynammicField[];
  @Input() cardTitle:string = ""
  @Output() onFormChange = new EventEmitter<any>();
  @Output() onValueChange = new EventEmitter<{
    fieldName: string;
    value: any;
  }>();

  

  form!: FormGroup

  private dynamicFormService = inject(DynamicFormService);
  private destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.form = this.dynamicFormService.buildForm(this.fields);

    this.form.valueChanges
    .pipe(takeUntil(this.destroy$))
    .subscribe(forValue => {
      this.onFormDataChange(forValue);
    })
  }

  onValueDataChange(fieldName: string, event: Event): any {
    const target = event.target as HTMLInputElement | HTMLSelectElement;
    const value = target.value;

    this.onValueChange.emit({ fieldName, value });
  }

  onFormDataChange(formValue: any) {
    this.onFormChange.emit(formValue);
  }

  patchFormData(data: any,options?:{emitEvent:boolean}) {
    this.form.patchValue(data,options);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
