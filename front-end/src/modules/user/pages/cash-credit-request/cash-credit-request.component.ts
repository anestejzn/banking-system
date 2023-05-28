import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { User } from 'src/modules/shared/model/user';
import { CashCreditRequest } from '../../model/cash-credit-request';
import { CashCreditService } from '../../service/cash-credit.service';
import { Debit } from 'src/modules/shared/model/debit';
import { ToastrService } from 'ngx-toastr';
import { DebitService } from 'src/modules/shared/service/debit-service/debit.service';

@Component({
  selector: 'app-cash-credit-request',
  templateUrl: './cash-credit-request.component.html',
  styleUrls: ['./cash-credit-request.component.scss']
})
export class CashCreditRequestComponent implements OnInit {

  firstPage = true;
  authSubscription:Subscription;
  cashCreditSubscription: Subscription;
  debitSubscription: Subscription;
  loggedUser:User;
  processedDebit: Debit;

  constructor(
    private authService: AuthService,
    private cashCreditService: CashCreditService,
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

  sendRequestCashCredit(event){
    const request: CashCreditRequest = {
      clientId: this.loggedUser.id,
      amount: event.amount,
      paymentPeriod: event.paymentPeriod
    }

    this.cashCreditSubscription = this.cashCreditService.sendCashCreditRequest(request).subscribe(
      debit => {
        this.processedDebit = debit;
        this.firstPage = false;
      },
      error => {
        this.toast.error(error.error, "Error happened");
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
          this.toast.info("Your debit request is cancelled.", "Info");
        },
        error => {
          this.toast.error(error.error, "Error happened");
        }
      )
    }
  }

}
