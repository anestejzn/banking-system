import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { AuthInterceptor } from './interceptors/auth-interceptor';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { MaterialModule } from '../material/material.module';
import { DateFormatPipe } from './pipes/date-format.pipe';


@NgModule({
  declarations: [
    ConfirmationDialogComponent,
    DateFormatPipe
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-left',
      preventDuplicates: true,
      closeButton: true,
    })
  ],
  exports: [
    DateFormatPipe
  ],
  providers:[
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
})
export class SharedModule { }
