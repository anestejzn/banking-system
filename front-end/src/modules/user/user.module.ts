import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UserRoutes } from './user.routes';

import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './pages/home/home.component';
import { CashCreditRequestComponent } from './pages/cash-credit-request/cash-credit-request.component';
import { CashCreditFirstPageComponent } from './components/cash-credit-first-page/cash-credit-first-page.component';
import { CashCreditSecondPageComponent } from './components/cash-credit-second-page/cash-credit-second-page.component';


@NgModule({
  declarations: [
   HomeComponent,
   CashCreditRequestComponent,
   CashCreditFirstPageComponent,
   CashCreditSecondPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(UserRoutes)
  ],
  exports:[
    HomeComponent
  ]
})
export class UserModule { }
