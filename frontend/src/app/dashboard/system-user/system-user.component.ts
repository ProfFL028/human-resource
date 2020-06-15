import { Component, OnInit } from '@angular/core';
import {SystemUser} from "./system-user.entity";
import {SystemUserService} from "./system-user.service";

@Component({
  selector: 'app-system-user',
  templateUrl: './system-user.component.html',
  styleUrls: ['./system-user.component.css']
})
export class SystemUserComponent implements OnInit {
  systemUsers: SystemUser[];
  constructor(private systemUserService: SystemUserService) { }

  ngOnInit() {
    this.systemUserService.findAll().subscribe(data => {
      console.log(data);
      this.systemUsers = data
    })
  }

  onAdd() {

  }
}
