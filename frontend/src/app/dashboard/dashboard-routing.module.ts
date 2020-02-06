import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule, Routes} from "@angular/router";
import {LayoutComponent} from "./layout/layout.component";
import {HeaderComponent} from "./layout/header/header.component";
import {SidebarComponent} from "./layout/sidebar/sidebar.component";
import {NgbDropdownModule} from "@ng-bootstrap/ng-bootstrap";
import {HomepageComponent} from "./homepage/homepage.component";
import {SystemUserComponent} from "./system-user/system-user.component";
import {AuthGuard} from "../shared/guard/auth.guard";

export const LayoutRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {path: '', redirectTo: 'homepage', pathMatch: 'prefix'},
      {path: 'homepage', component: HomepageComponent, pathMatch: 'full'},
      {path: 'system-user', component: SystemUserComponent, canActivate: [AuthGuard], data: {roles: ["ROLE_ADMIN"]}}
    ]
  }
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(LayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    NgbDropdownModule
  ],
  exports: [
    RouterModule
  ],
  declarations: [
    LayoutComponent,
    HeaderComponent,
    SidebarComponent,
    HomepageComponent,
    SystemUserComponent
  ],
  providers: []
})
export class DashboardRoutingModule {

}
