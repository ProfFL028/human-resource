import { Component, OnInit } from '@angular/core';
import {SystemDeptService} from "./system-dept.service"
import {SystemDept} from "./system-dept.entity"

@Component({
  selector: 'app-system-dept',
  templateUrl: './system-dept.component.html',
  styleUrls: ['./system-dept.component.css']
})
export class SystemDeptComponent implements OnInit {
  isLoading$ = this.systemDeptService.isLoading$
  systemDepts$ = this.systemDeptService.systemDepts$
  systemDept$ = this.systemDeptService.systemDept$

  isEditing = false;
  dialogTitle = "";

  constructor(private systemDeptService: SystemDeptService) { }

  ngOnInit() {
    this.systemDeptService.getSystemDeptTree()
  }

  onAdd() {
    this.isEditing = true;
    this.dialogTitle = "新增";
    this.systemDeptService.nextSystemDept(new SystemDept())
  }

  onEdit(systemDept: SystemDept) {
    this.isEditing = true;
    this.dialogTitle = `修改：${systemDept.shortName}`;
    this.systemDeptService.nextSystemDept(systemDept);
  }

  onDelete(id: number) {}


  onSubmit() {
    this.isEditing = false
  }

  onCancel() {
    this.isEditing = false
  }

}
