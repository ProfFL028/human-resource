import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {LocalStorageStrings} from "../shared/local-storage.strings";
import {User} from "./user/user.entity";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const user: User = JSON.parse(localStorage.getItem(LocalStorageStrings.CURRENT_USER));
    const requestWithToken = req.clone({
      headers: req.headers.append("X-Auth-Token", user.token)
    });

    return next.handle(requestWithToken);
  }

}
