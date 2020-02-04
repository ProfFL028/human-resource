import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "./user.entity";
import {ApiController} from "../../shared/api.controller";

@Injectable({providedIn: 'root'})
export class UserService {

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<User[]> {
    return this.http.get<User[]>(ApiController.USER_API_URL);
  }


  public save(user: User) {
    return this.http.post<User>(ApiController.USER_API_URL, user);
  }
}
