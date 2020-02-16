import {Component, Inject, Input, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {SystemRole} from "../system-role.entity";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng";

@Component({
  selector: 'app-system-role-detail',
  templateUrl: './system-role-detail.component.html',
  styleUrls: ['./system-role-detail.component.css']
})
export class SystemRoleDetailComponent implements OnInit {
  @Input() systemRole: SystemRole;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) {
    this.systemRole = this.config.data.systemRole;
  }

  ngOnInit() {
  }

  onSubmit() {
    this.ref.close(this.systemRole);
  }

  onCancel() {
    this.ref.close();
  }

}
