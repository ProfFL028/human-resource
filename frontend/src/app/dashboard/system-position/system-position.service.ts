import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {SystemPosition} from "./system-position.entity";
import {ApiController} from "../../shared/api.controller";
import {BehaviorSubject} from "rxjs";
import {catchError, share, shareReplay, tap} from "rxjs/operators"
import {SystemDept} from '../system-dept/system-dept.entity'

@Injectable()
export class SystemPositionService {
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _systemPositions$ = new BehaviorSubject<SystemPosition[]>([]);
  private _systemPosition$ = new BehaviorSubject<SystemPosition>(new SystemPosition());

  private _deptPositionLoading$ = new BehaviorSubject<boolean>(false)
  private _deptPositions$ = new BehaviorSubject<SystemPosition[]>([])

  get loading$() {
    return this._loading$.asObservable();
  }
  get systemPosition$() {
    return this._systemPosition$.asObservable();
  }
  get systemPositions$() {
    return this._systemPositions$.asObservable();
  }

  get deptPositionLoading$() {
    return this._deptPositionLoading$.asObservable()
  }

  get deptPositions$() {
    return this._deptPositions$.asObservable()
  }

  nextSystemPosition(systemPosition: SystemPosition) {
    this._systemPosition$.next(systemPosition);
  }

  constructor(public http: HttpClient) {
  }

  findAll() {
    this._loading$.next(true);
    return this.http.get<SystemPosition[]>(ApiController.POSITION_API_URL).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._systemPositions$.next(data);
        this._loading$.next(false);
      }
    )
  }

  getPositionsBy(dept: SystemDept) {
    this._deptPositionLoading$.next(true)
    return this.http.get<SystemPosition[]>(ApiController.POSITION_API_URL + '/systemDept/available/' + dept.id).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._deptPositionLoading$.next(false)
        this._deptPositions$.next(data)
      }
    )
  }

  save(systemPosition: SystemPosition) {
    this._loading$.next(true);
    return this.http.post<SystemPosition>(ApiController.POSITION_API_URL, systemPosition).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._systemPosition$.next(data);
        this._loading$.next(false);
        this.findAll();
      }
    )
  }

  delete(id: number) {
    this._loading$.next(true);
    return this.http.delete(ApiController.POSITION_API_URL + "/" + id).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._systemPosition$.next(new SystemPosition());
        this._loading$.next(false);
        this.findAll();
      }
    )
  }

  findAvailablePositions(systemDept: SystemDept) {
    return this.http.get<SystemPosition[]>(ApiController.POSITION_API_URL + "/systemDept/available/" + systemDept.id).pipe(
      shareReplay()
    )
  }

  findOwnedPositions(systemDept: SystemDept) {
    return this.http.get<SystemPosition[]>(ApiController.POSITION_API_URL + "/systemDept/owned/" + systemDept.id).pipe(
      shareReplay()
    )
  }

  toggleStatus(id: number) {
    this.http.post(ApiController.POSITION_API_URL + "/status/" + id,"").pipe(
      shareReplay()
    ).subscribe()
  }
}
