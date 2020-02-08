import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {LocalStorageStrings} from "../local-storage.strings";

@Injectable({providedIn: "root"})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (localStorage.getItem(LocalStorageStrings.IS_LOGGED_IN)) {
      if (route.data.roles && route.data.roles.indexOf("") === -1) {
        this.router.navigate(['/dashboard/homepage']).then()
      }
      return true
    }

    this.router.navigate(['/login']).then();
    return false;
  }
}
