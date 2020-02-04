import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {ApiController} from "../shared/api.controller";
import {catchError, map} from "rxjs/operators";
import {User} from "./user/user.entity";
import {throwError} from "rxjs";

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

    return this.http.post<User>(ApiController.AUTH_URL, "1", httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = '未知错误';
    console.log(errorRes.status);
    switch (errorRes.status) {
      case 401:
        errorMessage = "用户名或密码错误"
        break;
      case 404:
        errorMessage = "未找到此页面"
        break;
    }
    return throwError(errorMessage);
  }
}
