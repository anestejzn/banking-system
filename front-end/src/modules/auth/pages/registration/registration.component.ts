import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { UserRegistrationRequest } from '../../model/model/registration_and_verification/user-registration-request';
import { matchPasswordsValidator } from 'src/modules/shared/validators/confirm-password.validator';
import { ClientService } from 'src/modules/shared/service/client-service/client.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  showSpiner: boolean = false;
  
  registrationSubscription: Subscription;
  
  constructor(
    private toast: ToastrService, 
    private router: Router,
    private clientService: ClientService
  ) {
    this.showSpiner = false;
  }

  ngOnInit(): void {}

  addressForm = new FormGroup({
      cityFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      streetNameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      streetNumberFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z1-9 ]*'),
      ]),
      postCodeFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[1-9][0-9]{4}'),
      ]),
  });

  accountTypeForm = new FormGroup({
    accountTypeNameFormControl: new FormControl('', [
        Validators.required
      ]),
  });

  employmentForm = new FormGroup({
      dateOfBirthFormControl: new FormControl('', [
        Validators.required
      ]),
      startedWorkingFormControl: new FormControl(''),
      monthlyIncomeFormControl: new FormControl(0, [
        Validators.required,
        Validators.pattern('[1-9][0-9]*')
      ]),
      employerNameFormControl: new FormControl(''),
      termsOfPIOFondAgreementFormControl: new FormControl(false)
  });

  basicInfoForm = new FormGroup(
    {
      emailFormControl: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
      nameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      surnameFormControl: new FormControl('', [
        Validators.required,
        Validators.pattern('[a-zA-Z ]*'),
      ]),
      passwordAgainFormControl: new FormControl('', [
        Validators.required,
        Validators.minLength(12),
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{12,100}$')
      ]),
      passwordFormControl: new FormControl('', [
        Validators.required,
        Validators.minLength(12),
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{12,100}$')
      ]),
      roleFormControl: new FormControl('', [
        Validators.required,
      ]),
    },
    [matchPasswordsValidator()]
  );

  print(): void {
    console.log(this.basicInfoForm)
  }

  register(): void {
    if (this.basicInfoForm.hasError('mismatch')) {
      this.toast.error('Passwords not match');
    } else if (this.basicInfoForm.invalid || this.addressForm.invalid
              || this.accountTypeForm.invalid || this.employmentForm.invalid) {
      this.toast.error('Registration form is invalid.')
    } else {
      const newUser: UserRegistrationRequest = {
        email: this.basicInfoForm.get('emailFormControl').value,
        name: this.basicInfoForm.get('nameFormControl').value,
        surname: this.basicInfoForm.get('surnameFormControl').value,
        password: this.basicInfoForm.get('passwordFormControl').value,
        confirmPassword: this.basicInfoForm.get('passwordAgainFormControl').value,
        streetName: this.addressForm.get('streetNameFormControl').value,
        streetNumber: this.addressForm.get('streetNumberFormControl').value,
        postCode: this.addressForm.get('postCodeFormControl').value,
        city: this.addressForm.get('cityFormControl').value,
        dateOfBirth: new Date(this.employmentForm.get('dateOfBirthFormControl').value),
        monthlyIncome: this.employmentForm.get('monthlyIncomeFormControl').value,
        accountTypeName: this.accountTypeForm.get('accountTypeNameFormControl').value,
        termsOfPIOFondAgreement: this.employmentForm.get('termsOfPIOFondAgreementFormControl').value,
        employerName: this.employmentForm.get('employerNameFormControl').value,
        startedWorking: new Date(this.employmentForm.get('startedWorkingFormControl').value)
      }
      console.log(newUser)

      this.showSpiner = true;
      const isRetiree: boolean = this.basicInfoForm.get('roleFormControl').value === 'RETIREE';
      this.registrationSubscription = this.clientService
            .registerRegularUser(newUser, isRetiree)
            .subscribe(
              response => {
                this.showSpiner = false;
                this.toast.success(
                  'Please go to your email to verify account!',
                  'Registration successfully'
                );
                this.router.navigate([`/banking-system/auth/login`]);
              },
              error => {
                this.showSpiner = false;
                this.toast.error(error.error, 'Registration failed')
              }
            );
    }
  }

  ngOnDestroy(): void {
    if (this.registrationSubscription) {
      this.registrationSubscription.unsubscribe();
    }  
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}
