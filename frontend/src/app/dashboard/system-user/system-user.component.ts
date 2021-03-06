import {Component, OnInit} from '@angular/core'
import {SystemUser} from "./system-user.entity"
import {SystemUserService} from "./system-user.service"
import {ConfirmationService, DialogService} from 'primeng'
import {SystemUserDetailComponent} from './system-user-detail/system-user-detail.component'
import {FormControl} from '@angular/forms'
import {SystemDept} from '../system-dept/system-dept.entity'

@Component({
  selector: 'app-system-user',
  templateUrl: './system-user.component.html',
  styleUrls: ['./system-user.component.css'],
  providers: [
    ConfirmationService,
    DialogService
  ]
})
export class SystemUserComponent implements OnInit {
  systemUsers$ = this.systemUserService.users$
  loading$ = this.systemUserService.loading$


  constructor(private systemUserService: SystemUserService, private confirmationService: ConfirmationService, private dialogService: DialogService) {
  }

  ngOnInit() {
    this.systemUserService.findAll()
  }

  onAdd() {
    const dialogRef = this.dialogService.open(SystemUserDetailComponent, {
      data: {
        systemUser: new SystemUser()
      },
      header: `新增用户`,
      closable: false,
      width: '50%',
    })

    dialogRef.onClose.subscribe( (systemUser: SystemUser) => {
      this.systemUserService.findAll()
    })
  }

}
