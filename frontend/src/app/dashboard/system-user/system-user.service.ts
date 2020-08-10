import {HttpClient} from "@angular/common/http";
import {ApiController} from "../../shared/api.controller";
import {catchError, map, shareReplay} from "rxjs/operators"
import {SystemUser} from "./system-user.entity";
import { Injectable } from "@angular/core";
import {handleError} from '../../shared/error'
import {BehaviorSubject} from 'rxjs'


@Injectable()
export class SystemUserService {
  constructor(private http: HttpClient) {
  }

  private _loading$ = new BehaviorSubject<boolean>(false)
  private _users$ = new BehaviorSubject<SystemUser[]>([])

  get loading$() {
    return this._loading$.asObservable()
  }

  get users$() {
    return this._users$.asObservable()
  }

  findAll() {
    this._loading$.next(true)
    return this.http.get<SystemUser[]>(ApiController.USER_API_URL).pipe(shareReplay()).subscribe(
      data => {
        this._loading$.next(false)
        this._users$.next(data)
      }
    );
  }

  save(systemUser: SystemUser) {
    return this.http.post<SystemUser>(ApiController.USER_API_URL, systemUser).pipe(
      shareReplay(),
      catchError(handleError)
    )
  }
}
