import {Component, OnDestroy, OnInit} from '@angular/core';
import {SystemRole} from "./system-role.entity";
import {SystemRoleService} from "./system-role.service";
import {Observable} from "rxjs";
import {SystemRoleDetailComponent} from "./system-role-detail/system-role-detail.component";
import {DialogService} from "primeng";

@Component({
  selector: 'app-system-role',
  templateUrl: './system-role.component.html',
  styleUrls: ['./system-role.component.css'],
  providers: [DialogService]
})
export class SystemRoleComponent implements OnInit {
  isLoading$: Observable<boolean>;
  systemRoles$: Observable<SystemRole[]>;
  systemRole$: Observable<SystemRole>;

  constructor(private systemRoleService: SystemRoleService, private dialogService: DialogService) {
    this.isLoading$ = systemRoleService._loading$.asObservable();
    this.systemRoles$ = systemRoleService._systemRoles$.asObservable();
    this.systemRole$ = systemRoleService._systemRole$.asObservable();
  }

  ngOnInit() {
    this.systemRoleService.findAll();
  }

  onAdd() {
    this.openDialog(new SystemRole());
  }

  onEdit(systemRole: SystemRole) {
    this.openDialog(systemRole);
  }

  onDelete(id: number) {
    this.systemRoleService.delete(id);
  }

  onRowSelected(event) {
  }

  private openDialog(systemRole: SystemRole) {
    const dialogRef = this.dialogService.open(SystemRoleDetailComponent, {
      showHeader: false,
      data: {systemRole: systemRole}
    });

    dialogRef.onClose.subscribe(result => {
      if (result != null) {
        this.systemRoleService.save(result);
      }
    });
  }
}
