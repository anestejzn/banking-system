import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthRoutes } from './auth.routes';
import { RegistrationComponent } from './pages/registration/registration.component';
import { VerificationComponent } from './pages/verification/verification.component';
import { SuccessfulVerificationComponent } from './pages/successful-verification/successful-verification/successful-verification.component';
import { LoginComponent } from './pages/login/login.component';
import { SharedModule } from '../shared/shared.module';
import { BasicInfoRegistrationComponent } from './components/basic-info-registration/basic-info-registration.component';
import { AddressRegistrationComponent } from './components/address-registration/address-registration.component';
import { AccountTypeRegistrationComponent } from './components/account-type-registration/account-type-registration.component';
import { EmploymentRegistrationComponent } from './components/employment-registration/employment-registration.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    VerificationComponent,
    SuccessfulVerificationComponent,
    BasicInfoRegistrationComponent,
    AddressRegistrationComponent,
    AccountTypeRegistrationComponent,
    EmploymentRegistrationComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(AuthRoutes)
  ]
})
export class AuthModule { }
