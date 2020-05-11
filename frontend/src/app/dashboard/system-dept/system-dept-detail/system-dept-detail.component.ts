import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core'
import {SystemDeptService} from '../system-dept.service'
import {SystemDept} from '../system-dept.entity'
import {FormBuilder, FormControl, FormGroup} from '@angular/forms'
import {zh} from '../../../shared/locale-zh'
import {Observable, of} from 'rxjs'
import {debounceTime, distinctUntilChanged, map, startWith, switchMap} from 'rxjs/operators'
import {SystemDeptBenjiService} from '../system-dept-benji.service'

@Component({
  selector: 'app-system-dept-detail',
  templateUrl: './system-dept-detail.component.html',
  styleUrls: ['./system-dept-detail.component.css']
})
export class SystemDeptDetailComponent implements OnInit, OnChanges {
  @Input() systemDept: SystemDept = new SystemDept()
  @Output() onSubmitClick = new EventEmitter<SystemDept>()
  @Output() onCancelClick = new EventEmitter()

  systemDeptOptions: SystemDept[]
  systemDeptOptions$ = this.systemDeptBenjiService.systemDeptBenjiOptions$
  isOptionsLoading$ = this.systemDeptBenjiService.isSystemDeptBenjiLoading$

  myZH = zh
  parentDeptControl = new FormControl()

  constructor(private systemDeptService: SystemDeptService,
              private systemDeptBenjiService: SystemDeptBenjiService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.systemDeptBenjiService.getSystemDeptBenjiOptions()
    this.systemDeptOptions$.subscribe(data => {
      this.systemDeptOptions = data
    })

    this.systemDeptOptions$ = this.parentDeptControl.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      distinctUntilChanged(),
      map(value => this.filter(value))
    )

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.systemDept && this.systemDept.parent) {
      this.parentDeptControl.setValue(this.systemDept.parent)
    }
    this.systemDeptBenjiService.getSystemDeptBenjiOptions()
  }

  private filter(value: string): SystemDept[] {
    return this.systemDeptOptions.filter(data => {
      return data.shortName.indexOf(value) === 0 || data.deptNumber.indexOf(value) === 0
    })
  }

  onSubmit() {
    this.systemDept.parent = this.parentDeptControl.value
    this.systemDeptService.save(this.systemDept)
    this.onSubmitClick.emit(this.systemDept)
  }

  displayFn(systemDept?: SystemDept): string {
    return systemDept && systemDept.shortName ? systemDept.shortName : ''
  }

  onCancel() {
    this.onCancelClick.emit()
  }

}
