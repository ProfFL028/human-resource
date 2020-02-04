import {Component, OnInit} from '@angular/core';
import {User} from "./user.entity";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "./user.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user: User;
  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {
    this.user = new User();
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.save(this.user).subscribe(() => {
      this.router.navigate(['/users']);
    })
  }

}
