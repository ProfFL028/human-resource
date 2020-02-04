import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserListComponent} from "./auth/user/user-list/user-list.component";
import {UserComponent} from "./auth/user/user.component";

const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'addUser', component: UserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
