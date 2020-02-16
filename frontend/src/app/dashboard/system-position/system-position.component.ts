import {Component, OnInit} from '@angular/core';
import {SystemPositionService} from "./system-position.service";
import {Observable} from "rxjs";
import {SystemPosition} from "./system-position.entity";

@Component({
  selector: 'app-system-position',
  templateUrl: './system-position.component.html',
  styleUrls: ['./system-position.component.css']
})
export class SystemPositionComponent implements OnInit {
  loading$: Observable<boolean>;
  systemPositions$: Observable<SystemPosition[]>;
  systemPosition$: Observable<SystemPosition>;
  dialogTitle = "";
  isEditing = false;

  constructor(private systemPositionService: SystemPositionService) {
    this.loading$ = systemPositionService._loading$.asObservable();
    this.systemPosition$ = systemPositionService._systemPosition$.asObservable();
    this.systemPositions$ = systemPositionService._systemPositions$.asObservable();
  }

  ngOnInit() {
    this.systemPositionService.findAll();
  }

  onAdd() {
    this.dialogTitle = "新增岗位";
    this.isEditing = true;
    this.systemPositionService._systemPosition$.next(new SystemPosition());
  }

  onRowSelected(event) {

  }

  onEdit(systemPosition: SystemPosition) {
    this.dialogTitle = `修改岗位：${systemPosition.name}`;
    this.isEditing = true;
    this.systemPositionService._systemPosition$.next(systemPosition);
  }

  onDelete(id: number) {
    this.isEditing = false;
  }

  onSubmit() {
    this.isEditing = false;
  }

  onCancel() {
    this.isEditing = false;
  }

}
