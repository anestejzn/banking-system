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
import { ChartsPageComponent } from './pages/charts-page/charts-page.component';
import { ChartTabComponent } from './components/chart-tab/chart-tab.component';
import { ChartGraphComponent } from './components/chart-graph/chart-graph.component';
import { MatNativeDateModule } from '@angular/material/core'
import { MatDatepickerModule } from '@angular/material/datepicker'


@NgModule({
  declarations: [
   HomeComponent,
   RegistrationReqsTableComponent,
   RegistrationReqDetailsDialogComponent,
   ChartsPageComponent,
   ChartTabComponent,
   ChartGraphComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    MatNativeDateModule,
    MatDatepickerModule,
    SharedModule,
    RouterModule.forChild(AdminRoutes)
  ],
  exports:[
    HomeComponent
  ]
})
export class AdminModule { }
