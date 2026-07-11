import { Injectable } from '@angular/core';
import { DynammicField } from './dynamic-ui.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DynamicFormService {

  buildForm(fields:DynammicField[]){
    const controls:any = [];

    fields.forEach(field=>{
      controls[field.name] = new FormControl(
        {
          value:field?.defaultValue ?? null,
          disabled:field?.disabled ?? false
        },
        field.validators ?? []
      )
    })
    return new FormGroup(controls);
  }
}
