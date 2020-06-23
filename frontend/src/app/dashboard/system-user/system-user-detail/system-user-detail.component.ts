import {Component, OnInit} from '@angular/core'
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng'
import {SystemUser} from '../system-user.entity'
import {SystemUserService} from '../system-user.service'
import {FormControl} from '@angular/forms'
import {SystemDept} from '../../system-dept/system-dept.entity'
import {Observable} from 'rxjs'

import {systemDeptDisplayForAutoComplete} from '../../system-dept/system-dept.entity'
import {displaySystemPosition, SystemPosition} from '../../system-position/system-position.entity'
import {SystemDeptService} from '../../system-dept/system-dept.service'
import {SystemPositionService} from '../../system-position/system-position.service'

@Component({
  selector: 'app-system-user-detail',
  templateUrl: './system-user-detail.component.html',
  styleUrls: ['./system-user-detail.component.css']
})
export class SystemUserDetailComponent implements OnInit {

  systemUser: SystemUser

  deptControl: FormControl = new FormControl()
  positionControl: FormControl = new FormControl()

  isDeptLoading$: Observable<boolean> = this.systemDeptService.isTrueSystemDeptLoading$
  systemDepts$: Observable<SystemDept[]> = this.systemDeptService.trueSystemDepts$

  isPositionLoading$: Observable<boolean> = this.systemPositionService.deptPositionLoading$
  systemPositions$: Observable<SystemPosition[]> = this.systemPositionService.deptPositions$

  constructor(
    private systemUserService: SystemUserService,
    private systemDeptService: SystemDeptService,
    private systemPositionService: SystemPositionService,
    private dialogRef: DynamicDialogRef,
    private dialogConfig: DynamicDialogConfig) {
  }

  ngOnInit(): void {
    this.systemUser = this.dialogConfig.data.systemUser

    this.systemDeptService.getTrueSystemDeptOptions()
  }

  displaySystemDept(systemDept?: SystemDept): string {
    return systemDeptDisplayForAutoComplete(systemDept)
  }

  changePosition(event) {
    this.systemPositionService.getPositionsBy(event.option.value)
  }

  displayPositionFn(systemPosition?: SystemPosition): string {
    return displaySystemPosition(systemPosition)
  }

  onSubmit() {
    this.systemUser.dept = this.deptControl.value
    this.systemUser.position = this.positionControl.value
    console.log(this.systemUser)
  }

  onCancel() {
    this.dialogRef.close()
  }
}
