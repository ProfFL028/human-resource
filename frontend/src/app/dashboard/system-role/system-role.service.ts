import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ApiController} from "../../shared/api.controller";
import {catchError, shareReplay} from "rxjs/operators";
import {SystemRole} from "./system-role.entity";
import {BehaviorSubject, of, throwError} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class SystemRoleService {
  _loading$ = new BehaviorSubject<boolean>(true);
  _systemRoles$ = new BehaviorSubject<SystemRole[]>([]);
  _systemRole$ = new BehaviorSubject<SystemRole>(null);

  constructor(private http: HttpClient) {
  }

  findAll() {
    this._loading$.next(true);
    return this.http.get<SystemRole[]>(ApiController.ROLE_API_URL).pipe(shareReplay(), catchError(this.handleError)).subscribe(
      result => {
        this._systemRoles$.next(result);
        this._loading$.next(false);
      }
    );
  }

  save(systemRole: SystemRole) {
    this._loading$.next(true);
    return this.http.post<SystemRole>(ApiController.ROLE_API_URL, systemRole).pipe(shareReplay(), catchError(this.handleError)).subscribe(
      result => {
        this._systemRole$.next(result);
        this._loading$.next(false);
      }
    );
  }

  delete(id: number) {
    return this.http.delete(ApiController + "/" + id).pipe(shareReplay(), catchError(this.handleError)).subscribe();
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = '未知错误';
    switch (errorRes.status) {
      case 400:
        errorMessage = "内部错误";
        break;
      case 401:
        errorMessage = "没有权限";
      default:
        break;
    }
    return of(null)
  }
}
