import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';

import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './pages/home/home.component';
import { RegistrationReqsTableComponent } from './components/registration-reqs-table/registration-reqs-table.component';
import { RegistrationReqDetailsDialogComponent } from './components/registration-req-details-dialog/registration-req-details-dialog.component';


@NgModule({
  declarations: [
   HomeComponent,
   RegistrationReqsTableComponent,
   RegistrationReqDetailsDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(AdminRoutes)
  ],
  exports:[
    HomeComponent
  ]
})
export class AdminModule { }
