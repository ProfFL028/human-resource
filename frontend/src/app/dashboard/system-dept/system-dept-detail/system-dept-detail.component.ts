import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core'
import {SystemDeptService} from '../system-dept.service'
import {SystemDept} from '../system-dept.entity'
import {FormBuilder, FormControl, FormGroup} from '@angular/forms'
import {zh} from '../../../shared/locale-zh'
import {Observable, of} from 'rxjs'
import {debounceTime, distinctUntilChanged, map, startWith, switchMap} from 'rxjs/operators'

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
  systemDeptOptions$ = this.systemDeptService.systemDeptOptions$
  isOptionsLoading$ = this.systemDeptService.isOptionsLoading$

  filterDepts$: Observable<SystemDept[]>

  myZH = zh
  parentDeptControl = new FormControl()


  constructor(private systemDeptService: SystemDeptService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.systemDeptOptions$.subscribe( data => {
      this.systemDeptOptions = data;
    })
    this.systemDeptService.getSystemDeptOptions()

    this.systemDeptOptions$ = this.parentDeptControl.valueChanges.pipe(
      startWith(this.systemDept.parent?.shortName),
      debounceTime(300),
      distinctUntilChanged(),
      map(value => this.filter(value))
    )

  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('ngOnChange:' + this.systemDept.parent)
    if (this.systemDept && this.systemDept.parent) {
      this.parentDeptControl.setValue(this.systemDept.parent)

      console.log('after set:' + this.parentDeptControl.value)
    }
  }

  private filter(value: string): SystemDept[] {
    return this.systemDeptOptions.filter(data => {
      return data.shortName.indexOf(value) === 0 ||  data.deptNumber.indexOf(value) === 0
    })
  }

  onSubmit() {
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
