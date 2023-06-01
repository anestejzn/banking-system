import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { User } from 'src/modules/shared/model/user';
import { OverdraftRequest } from '../../model/overdraft-request';
import { Debit } from 'src/modules/shared/model/debit';
import { OverdraftService } from '../../service/overdraft.service';
import { ToastrService } from 'ngx-toastr';
import { DebitService } from 'src/modules/shared/service/debit-service/debit.service';

@Component({
  selector: 'app-overdraft-request',
  templateUrl: './overdraft-request.component.html',
  styleUrls: ['./overdraft-request.component.scss']
})
export class OverdraftRequestComponent implements OnInit {

  firstPage = true;
  authSubscription:Subscription;
  overdraftSubscription: Subscription;
  debitSubscription: Subscription;
  loggedUser:User;
  processedDebit: Debit;

  constructor(
    private authService: AuthService, 
    private overdraftService: OverdraftService, 
    private toast: ToastrService,
    private debitService: DebitService
  ) { }

  ngOnInit(): void {
    this.authSubscription = this.authService
    .getSubjectCurrentUser()
    .subscribe(user => {
      this.loggedUser = user;
    });
  }


  sendRequestOverdraft(event){
    console.log(event);
    const request: OverdraftRequest = {
      clientId: this.loggedUser.id,
      firstMonthlyIncome: event.firstMonthlyIncome,
      secondMonthlyIncome: event.secondMonthlyIncome,
      thirdMonthlyIncome: event.thirdMonthlyIncome
    };

    this.overdraftSubscription = this.overdraftService.sendOverdraftRequest(request).subscribe(
      debit => {
        this.processedDebit = debit;
        this.firstPage = false;
        console.log(debit);
      },
      error => {
        this.toast.error(error.error);
      }
    )
  }

  answerFromSecondPage(event){
    if(event === "back"){
      this.firstPage = true;
    }
    else if(event === "no"){
      this.firstPage = true;
      this.debitSubscription = this.debitService.cancelDebitRequest(this.processedDebit.id).subscribe(
        response => {
          this.toast.info("Your overdraft request is cancelled.");
        },
        error => {
          this.toast.error(error.error);
        }
      )
    }
  }

  


}
