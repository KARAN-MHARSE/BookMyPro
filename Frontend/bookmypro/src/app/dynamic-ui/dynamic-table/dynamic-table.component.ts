import { Component, EventEmitter, input, Input, Output } from '@angular/core';
import { DynamicTableColumn } from '../dynamic-ui.model';

@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrl: './dynamic-table.component.css'
})
export class DynamicTableComponent {

  @Input() columns!:DynamicTableColumn[]
  @Output() onGridCellClick = new EventEmitter();
  @Input() tableData!:any

  onGridCellChange(column:any,row:any,event:any){
    const target = event?.target as HTMLInputElement | HTMLSelectElement
    row[column.field] =  target.value
    return row;
  }

  onGridCellClickEvent(rowData:any){
    this.onGridCellClick.emit(rowData)
  }
}
