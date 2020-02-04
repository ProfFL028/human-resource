import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './auth/user/user-list/user-list.component';
import { UserComponent } from './auth/user/user.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './auth/login/login.component';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSliderModule} from "@angular/material/slider";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule} from "@angular/material/input";
import { HomepageComponent } from './homepage/homepage.component';
import { LoadingComponent } from './shared/loading/loading.component';


@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserComponent,
    LoginComponent,
    HomepageComponent,
    LoadingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    MatCardModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatSliderModule,
    BrowserAnimationsModule,
    MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
