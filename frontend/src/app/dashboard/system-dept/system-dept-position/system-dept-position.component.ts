import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core'
import {SystemPosition} from '../../system-position/system-position.entity'
import {SystemDept} from '../system-dept.entity'
import {SystemPositionService} from '../../system-position/system-position.service'
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng'
import {SystemDeptService} from '../system-dept.service'

@Component({
  selector: 'app-system-dept-position',
  templateUrl: './system-dept-position.component.html',
  styleUrls: ['./system-dept-position.component.css']
})
export class SystemDeptPositionComponent implements OnInit {
  sourcePositions: SystemPosition[] = []
  targetPositions: SystemPosition[] = []

  @Input() systemDept = new SystemDept()
  @Output() onSubmitClick = new EventEmitter<SystemPosition[]>()
  @Output() onCancelClick = new EventEmitter()

  constructor(private systemPositionService: SystemPositionService, private systemDeptService: SystemDeptService,
              private dialogRef: DynamicDialogRef, private dialogConfig: DynamicDialogConfig) {
  }

  ngOnInit(): void {
    this.systemDept = this.dialogConfig.data.systemDept

    this.systemPositionService.findAvailablePositions(this.systemDept).subscribe(data => {
      this.sourcePositions = data
    })
    this.systemPositionService.findOwnedPositions(this.systemDept).subscribe(data => {
      this.targetPositions = data
    })
  }

  onSave() {
    this.systemDeptService.modifyPositions(this.systemDept, this.targetPositions).subscribe(() => {
      this.dialogRef.close()
    })
  }

  onCancel() {
    this.dialogRef.close()
  }

}
