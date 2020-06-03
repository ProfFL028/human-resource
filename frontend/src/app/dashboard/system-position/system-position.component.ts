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
    this.loading$ = systemPositionService.loading$;
    this.systemPosition$ = systemPositionService.systemPosition$;
    this.systemPositions$ = systemPositionService.systemPositions$;
  }

  ngOnInit() {
    this.reloadPage();
    this.systemPositionService.findAll()
  }

  onAdd() {
    this.dialogTitle = "新增岗位";
    this.isEditing = true;
    this.systemPositionService.nextSystemPosition(new SystemPosition());
  }

  onRowSelected(event) {

  }

  onEdit(systemPosition: SystemPosition) {
    this.dialogTitle = `修改岗位：${systemPosition.name}`;
    this.isEditing = true;
    this.systemPositionService.nextSystemPosition(systemPosition);
  }

  onDelete(id: number) {
    this.reloadPage();
  }

  onSubmit() {
    this.reloadPage();
  }

  onCancel() {
    this.isEditing = false;
  }

  private reloadPage() {
    this.isEditing = false;
  }

  onChange(systemPosition: SystemPosition) {
    this.systemPositionService.toggleStatus(systemPosition.id)
  }
}
