import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminRoutes } from './admin.routes';

import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './pages/home/home.component';


@NgModule({
  declarations: [
   HomeComponent
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
