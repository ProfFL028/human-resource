import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {SystemPosition} from "./system-position.entity";
import {ApiController} from "../../shared/api.controller";
import {BehaviorSubject} from "rxjs";
import {catchError, shareReplay, tap} from "rxjs/operators";

@Injectable()
export class SystemPositionService {
  _loading$ = new BehaviorSubject<boolean>(true);
  _systemPositions$ = new BehaviorSubject<SystemPosition[]>([]);
  _systemPosition$ = new BehaviorSubject<SystemPosition>(null);

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

  save(systemPosition: SystemPosition) {
    this._loading$.next(true);
    return this.http.post<SystemPosition>(ApiController.POSITION_API_URL, systemPosition).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._systemPosition$.next(data);
        this._loading$.next(false);
      }
    )
  }

  delete(id: number) {
    this._loading$.next(true);
    return this.http.delete(ApiController.POSITION_API_URL + "/" + id).pipe(
      shareReplay()
    ).subscribe(
      data => {
        this._systemPosition$.next(null);
        this._loading$.next(false);
      }
    )
  }
}
