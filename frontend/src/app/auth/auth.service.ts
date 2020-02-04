import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiController} from "../shared/api.controller";
import {map} from "rxjs/operators";
import {User} from "./user/user.entity";

@Injectable({providedIn: 'root'})
export class AuthService {

  constructor(private http: HttpClient) {

  }

  login(username: string, password: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'X-Auth-Username': username,
        'X-Auth-Password': password
      })
    };

    return this.http.post<User>(ApiController.AUTH_URL, "1", httpOptions);


  }
}
