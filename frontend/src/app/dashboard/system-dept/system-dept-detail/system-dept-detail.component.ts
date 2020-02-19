import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core'
import {SystemDeptService} from "../system-dept.service"
import {SystemDept} from "../system-dept.entity"
import {FormControl, FormGroup} from "@angular/forms"

@Component({
  selector: 'app-system-dept-detail',
  templateUrl: './system-dept-detail.component.html',
  styleUrls: ['./system-dept-detail.component.css']
})
export class SystemDeptDetailComponent implements OnInit {
  @Input() systemDept: SystemDept
  @Output() onSubmitClick = new EventEmitter<SystemDept>()
  @Output() onCancelClick = new EventEmitter()

  form: FormGroup
  constructor(private systemDeptService: SystemDeptService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      deptNumber: new FormControl(this.systemDept.deptNumber),
      fullName: new FormControl(this.systemDept.fullName),
      shortName: new FormControl(this.systemDept.shortName),
      sortNumber: new FormControl(this.systemDept.sortNumber)
    })
  }

  onSubmit() {
    console.log(this.form.value)
    this.systemDeptService.save(this.form.value)
    this.onSubmitClick.emit(this.form.value)
  }

  onCancel() {
    this.onCancelClick.emit()
  }

}
