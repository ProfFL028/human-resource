import {Injectable} from '@angular/core'
import {BehaviorSubject} from 'rxjs'
import {SystemDept} from './system-dept.entity'
import {HttpClient} from '@angular/common/http'
import {ApiController} from '../../shared/api.controller'
import {shareReplay} from 'rxjs/operators'
import {TreeNode} from 'primeng'
import {DateFormat} from '../../shared/date-format'
import {SystemPosition} from '../system-position/system-position.entity'

@Injectable()
export class SystemDeptService {
  private _isLoading$ = new BehaviorSubject<boolean>(false)
  private _systemDept$ = new BehaviorSubject<SystemDept>(new SystemDept())
  private _systemDepts$ = new BehaviorSubject<TreeNode[]>([])

  private _isOptionsLoading$ = new BehaviorSubject<boolean>(false)
  private _systemDeptOptions$ = new BehaviorSubject<SystemDept[]>([])

  public systemDepts: TreeNode[]

  get isLoading$() {
    return this._isLoading$.asObservable()
  }

  get isOptionsLoading$() {
    return this._isOptionsLoading$.asObservable()
  }

  get systemDept$() {
    return this._systemDept$.asObservable()
  }

  get systemDepts$() {
    return this._systemDepts$.asObservable()
  }

  get systemDeptOptions$() {
    return this._systemDeptOptions$.asObservable()
  }

  constructor(public http: HttpClient) {
  }

  getSystemDeptOptions() {
    this._isOptionsLoading$.next(true)
    return this.http.get<SystemDept[]>(ApiController.DEPT_API_URL + '/options').pipe(
      shareReplay()
    ).subscribe(data => {
      console.log('getSystemDeptOptions: ' + data)
      this._systemDeptOptions$.next(data)
      this._isOptionsLoading$.next(false)
    })
  }


  getSystemDeptTree() {
    this._isLoading$.next(true)
    return this.http.get<TreeNode[]>(ApiController.DEPT_API_URL).pipe(
      shareReplay()
    ).subscribe((data) => {
      console.log(' getSystemDeptTree : ' + data)
      this._isLoading$.next(false)
      this._systemDepts$.next(data)

      this.systemDepts = data
    })
  }

  save(systemDept: SystemDept) {
    this._isLoading$.next(true)
    return this.http.post<SystemDept>(ApiController.DEPT_API_URL, {
      id: systemDept.id,
      fullName: systemDept.fullName,
      shortName: systemDept.shortName,
      deptNumber: systemDept.deptNumber,
      sortNumber: systemDept.sortNumber,
      beginDate: DateFormat.dateAsYYYYMMDD(systemDept.beginDate),
      endDate: DateFormat.dateAsYYYYMMDD(systemDept.endDate),
      parent: {
        id: systemDept.parent.id
      }
    }).pipe(
      shareReplay()
    ).subscribe(data => {
      this._systemDept$.next(data)
      this.getSystemDeptTree()
    })
  }

  delete(id: number) {
    this._isLoading$.next(true)
    this.http.delete(`${ApiController.DEPT_API_URL}/${id}`).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._systemDept$.next(new SystemDept())
        this.getSystemDeptTree()
      }
    )
  }

  nextSystemDept(systemDept: SystemDept) {
    this._systemDept$.next(this.cloneDept(systemDept))
  }

  cloneDept(systemDept: SystemDept): SystemDept {
    const newSystemDept = new SystemDept()
    newSystemDept.id = systemDept.id
    newSystemDept.fullName = systemDept.fullName
    newSystemDept.shortName = systemDept.shortName
    newSystemDept.deptNumber = systemDept.deptNumber
    newSystemDept.sortNumber = systemDept.sortNumber
    newSystemDept.beginDate = systemDept.beginDate
    newSystemDept.endDate = systemDept.endDate
    newSystemDept.parent = systemDept.parent
    newSystemDept.children = systemDept.children

    return newSystemDept
  }

  modifyPositions(systemDept: SystemDept, targetPositions: SystemPosition[]) {
    return this.http.post(`${ApiController.DEPT_API_URL}/modifyPositions/${systemDept.id}`, targetPositions).pipe(
      shareReplay()
    )
  }
}
