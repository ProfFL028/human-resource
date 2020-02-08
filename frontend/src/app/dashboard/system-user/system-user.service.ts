import {HttpClient} from "@angular/common/http";
import {ApiController} from "../../shared/api.controller";
import {map, shareReplay} from "rxjs/operators";
import {SystemUser} from "./system-user.entity";
import { Injectable } from "@angular/core";


@Injectable()
export class SystemUserService {
  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get<SystemUser[]>(ApiController.USER_API_URL).pipe(shareReplay());
  }
}
