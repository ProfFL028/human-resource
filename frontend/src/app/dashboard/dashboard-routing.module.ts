import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {LayoutComponent} from './layout/layout.component';
import {HeaderComponent} from './layout/header/header.component';
import {SidebarComponent} from './layout/sidebar/sidebar.component';
import {NgbDropdownModule} from '@ng-bootstrap/ng-bootstrap';
import {HomepageComponent} from './homepage/homepage.component';
import {SystemUserComponent} from './system-user/system-user.component';
import {SystemUserService} from './system-user/system-user.service';
import {SystemDeptComponent} from './system-dept/system-dept.component';
import {SystemRoleComponent} from './system-role/system-role.component';
import {SystemPositionComponent} from './system-position/system-position.component';
import {SystemRoleService} from './system-role/system-role.service';
import {SystemRoleDetailComponent} from './system-role/system-role-detail/system-role-detail.component';
import {LoadingRoutingModule} from '../shared/loading/loading-routing.module';
import {
  AutoCompleteModule,
  CalendarModule,
  ConfirmationService,
  ConfirmDialogModule,
  DialogModule, PickListModule,
  TableModule,
  TreeTableModule
} from 'primeng'
import {MatInputModule} from '@angular/material/input';
import {SystemPositionService} from './system-position/system-position.service';
import {SystemPositionDetailComponent} from './system-position/system-position-detail/system-position-detail.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {SystemDeptService} from './system-dept/system-dept.service';
import { SystemDeptDetailComponent } from './system-dept/system-dept-detail/system-dept-detail.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete'
import {SystemDeptBenjiService} from './system-dept/system-dept-benji.service';
import { SystemDeptPositionComponent } from './system-dept/system-dept-position/system-dept-position.component'

export const LayoutRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {path: '', redirectTo: 'homepage', pathMatch: 'prefix'},
      {path: 'homepage', component: HomepageComponent, pathMatch: 'full'},
      {path: 'system-user', component: SystemUserComponent}, // canActivate: [AuthGuard], data: {roles: ["ROLE_ADMIN"]}}
      {path: 'system-role', component: SystemRoleComponent},
      {path: 'system-position', component: SystemPositionComponent},
      {path: 'system-dept', component: SystemDeptComponent}
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(LayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    NgbDropdownModule,
    LoadingRoutingModule,
    TableModule,
    MatInputModule,
    DialogModule,
    MatCheckboxModule,
    TreeTableModule,
    CalendarModule,
    AutoCompleteModule,
    MatAutocompleteModule,
    ConfirmDialogModule,
    PickListModule
  ],
  exports: [
    RouterModule,
    SystemRoleDetailComponent
  ],
  declarations: [
    LayoutComponent,
    HeaderComponent,
    SidebarComponent,
    HomepageComponent,
    SystemUserComponent,
    SystemDeptComponent,
    SystemRoleComponent,
    SystemPositionComponent,
    SystemRoleDetailComponent,
    SystemPositionDetailComponent,
    SystemDeptDetailComponent,
    SystemDeptPositionComponent
  ],
  entryComponents: [
    SystemRoleDetailComponent
  ],
  providers: [
    SystemUserService,
    SystemRoleService,
    SystemPositionService,
    SystemDeptService,
    SystemDeptBenjiService
  ]
})
export class DashboardRoutingModule {

}
