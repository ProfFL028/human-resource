import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLoading = false;
  error = "";
  authForm: FormGroup;
  tellerNumberController = new FormControl('', [Validators.required])
  passwordController = new FormControl('', [Validators.required, Validators.minLength(6)])

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.initForm()
  }

  onLoginClick() {
    this.isLoading = true
    this.authService.login(this.tellerNumberController.value, this.passwordController.value).subscribe(
      data => {
        this.isLoading = false;
        this.error = "";
        this.router.navigate(["/dashboard"]).then(r => {
        })
      },
      error => {
        this.isLoading = false;
        this.error = error
      }
    );

    this.authForm.reset();
  }

  private initForm() {
    this.authForm = new FormGroup({
      tellerNumber: this.tellerNumberController,
      password: this.passwordController
    });
  }

}
