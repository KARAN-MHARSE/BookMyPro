import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DynamicFormComponent } from './dynamic-form/dynamic-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DynamicTableComponent } from './dynamic-table/dynamic-table.component';



@NgModule({
  declarations: [
    DynamicFormComponent,
    DynamicTableComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports:[
    DynamicFormComponent,
    DynamicTableComponent
  ]
})
export class DynamicUiModule { }
