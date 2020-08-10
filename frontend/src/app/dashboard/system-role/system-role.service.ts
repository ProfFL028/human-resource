import {HttpClient, HttpErrorResponse} from "@angular/common/http"
import {ApiController} from "../../shared/api.controller"
import {catchError, shareReplay, tap} from "rxjs/operators"
import {SystemRole} from "./system-role.entity"
import {BehaviorSubject, of} from "rxjs"
import {Injectable} from "@angular/core"
import {ResultValue} from '../../shared/ResultValue'

@Injectable()
export class SystemRoleService {
  _loading$ = new BehaviorSubject<boolean>(true)
  _systemRoles$ = new BehaviorSubject<SystemRole[]>([])
  _systemRole$ = new BehaviorSubject<SystemRole>(null)

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get<ResultValue<SystemRole>>(ApiController.ROLE_API_URL).pipe(shareReplay(),
      tap(() => {
        this._loading$.next(true)
      }),
      catchError(this.handleError)
    ).subscribe(data => {
      this._systemRoles$.next(data.data)
      this._loading$.next(false)
    })
  }

  save(systemRole: SystemRole) {
    return this.http.post<SystemRole>(ApiController.ROLE_API_URL, systemRole).pipe(shareReplay(),
      tap(data => {
        this._loading$.next(true)
      }),
      catchError(this.handleError)
    ).subscribe(
      data => {
        this._systemRole$.next(data)
        this._loading$.next(false)
        this.findAll()
      })
  }

  delete(id: number) {
    return this.http.delete(ApiController.ROLE_API_URL + "/" + id).pipe(shareReplay(),
      tap(data => {
        this._loading$.next(true)
      }),
      catchError(this.handleError)
    ).subscribe(data => {
      this._systemRole$.next(data)
      this._loading$.next(false)
      this.findAll()
    })
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = '未知错误'
    switch (errorRes.status) {
      case 400:
        errorMessage = "内部错误"
        break
      case 401:
        errorMessage = "没有权限"
      default:
        break
    }
    return of(null)
  }

  toggleStatus(id: number) {
    return this.http.post(ApiController.ROLE_API_URL + "/status/" + id, "").pipe(
      shareReplay(),
      catchError(this.handleError)).subscribe( data => {

    })
  }
}
