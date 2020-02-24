import {Inject, Injectable} from "@angular/core"
import {BehaviorSubject} from "rxjs"
import {SystemDept} from "./system-dept.entity"
import {HttpClient} from "@angular/common/http"
import {ApiController} from "../../shared/api.controller"
import {shareReplay, tap} from "rxjs/operators"
import {TreeNode} from "primeng"

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
    return this.http.get<SystemDept[]>(ApiController.DEPT_API_URL + "/options").pipe(
      shareReplay()
    ).subscribe(data => {
      console.log("getSystemDeptOptions: " + data)
      this._isOptionsLoading$.next(false)
      this._systemDeptOptions$.next(data)
    });
  }


  getSystemDeptTree() {
    this._isLoading$.next(true)
    return this.http.get<TreeNode[]>(ApiController.DEPT_API_URL).pipe(
      shareReplay()
    ).subscribe((data) => {
      console.log(" getSystemDeptTree : "  + data)
      this._isLoading$.next(false)
      this._systemDepts$.next(data)

      this.systemDepts = data
    })
  }

  save(systemDept: SystemDept) {
    this._isLoading$.next(true)
    return this.http.post<SystemDept>(ApiController.DEPT_API_URL, systemDept).pipe(
      shareReplay()
    ).subscribe(data => {
      this._systemDept$.next(data)
      this.getSystemDeptTree()
    })
  }

  delete(id: Number) {
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
    this._systemDept$.next(systemDept)
  }
}
