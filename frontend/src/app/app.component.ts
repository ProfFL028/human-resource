import {Component} from '@angular/core';
import {AuthService} from "./auth/auth.service";
import {BehaviorSubject, Observable, Subject, Subscription} from "rxjs";
import {User, UserDetail} from "./auth/user/user.entity";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  user = new User()

  constructor(private authService: AuthService) {}

  onListUsers() {
    this.authService.login("9712152", "9712152@smls").subscribe(data => {
      this.user = data
    })
  }
}

