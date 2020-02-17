import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {ApiController} from "../shared/api.controller";
import {catchError, map, shareReplay} from "rxjs/operators";
import {User} from "./user/user.entity";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {LocalStorageStrings} from "../shared/local-storage.strings";

@Injectable({providedIn: 'root'})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser$: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem(LocalStorageStrings.CURRENT_USER)));
    this.currentUser$ = this.currentUserSubject.asObservable();
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
      shareReplay(),
      map(user=> {
        localStorage.setItem(LocalStorageStrings.IS_LOGGED_IN, "true");
        localStorage.setItem(LocalStorageStrings.CURRENT_USER, JSON.stringify(user));
        this.currentUserSubject.next(user);
      }),
      catchError(this.handleError)
    );
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = '未知错误';
    console.log(errorRes);
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
