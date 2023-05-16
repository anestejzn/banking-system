import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginRequest } from '../../model/model/login/login-request';
import { Subscription } from 'rxjs';
import { AuthService } from '../../service/auth/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  enterPin = false;
  user = null;
  showSpiner = false;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(12),
    ]),
  });

  authSubscription: Subscription;
  hide = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
  }

  getErrorMessage() {
    if (this.loginForm.get('email').hasError('required')) {
      return 'Email is required';
    }

    return this.loginForm.get('email').hasError('email')
      ? 'Not a valid email'
      : '';
  }

  logIn() {
    if (!this.loginForm.invalid) {
      const loginRequest: LoginRequest = {
        email: this.loginForm.get('email').value,
        password: this.loginForm.get('password').value,
      };
      this.showSpiner = true;
      this.authSubscription = this.authService.login(loginRequest).subscribe(
        userResponse => {
          this.authService.setSessionStorage(userResponse);
          if(userResponse.user.role.roleName === 'ROLE_ADMIN'){
            this.router.navigate(['/banking-system/admin/home']);
          }
          else{
            this.router.navigate(['/banking-system/user/home']);
          }
        },
        error => {
          console.log(error.error);
          this.toast.error('Email or password is not correct!', 'Login failed');
        }
      );
    }
  }

  ngOnDestroy(){
    if(this.authSubscription){
      this.authSubscription.unsubscribe();
    }
  }


}
