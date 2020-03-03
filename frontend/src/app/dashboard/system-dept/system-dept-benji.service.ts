import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {BehaviorSubject} from 'rxjs'
import {SystemDept} from './system-dept.entity'
import {ApiController} from '../../shared/api.controller'
import {shareReplay} from 'rxjs/operators'

@Injectable()
export class SystemDeptBenjiService {
  constructor(private http: HttpClient) {
  }

  private _systemDeptBenjiOptions$ = new BehaviorSubject<SystemDept[]>([])
  private _isSystemDeptBenjiLoading$ = new BehaviorSubject<boolean>(false)

  get systemDeptBenjiOptions$() {
    return this._systemDeptBenjiOptions$.asObservable()
  }

  get isSystemDeptBenjiLoading$() {
    return this._isSystemDeptBenjiLoading$.asObservable()
  }

  getSystemDeptBenjiOptions() {
    this._isSystemDeptBenjiLoading$.next(true)
    return this.http.get<SystemDept[]>(ApiController.DEPT_API_URL + '/benjiOptions').pipe(
      shareReplay()
    ).subscribe(data => {
      console.log('getSystemDeptBenjiOptions: ' + data)
      this._systemDeptBenjiOptions$.next(data)
      this._isSystemDeptBenjiLoading$.next(false)
    })
  }
}
