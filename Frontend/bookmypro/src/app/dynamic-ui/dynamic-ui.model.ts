import { ValidatorFn } from "@angular/forms";

export interface DynammicField{
    type:'text' | 'number' | 'select' | 'checkbox' | 'radio' | 'date';
    name:string;
    label:string;
    defaultValue?:any;
    disabled?:boolean;
    validators?:ValidatorFn[];
    options?:{label:string,value:any}[];
    hidden?:boolean
}

export interface DynamicTableColumn {
    field: string;
    label: string;
    format?: TableFieldFormat;
}

export type TableFieldFormat = "text" | "number";