import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { TransactionRequest } from 'src/modules/shared/model/transaction';
import { User } from 'src/modules/shared/model/user';
import { TransactionService } from 'src/modules/shared/service/transaction-service/transaction.service';

@Component({
  selector: 'app-make-transaction',
  templateUrl: './make-transaction.component.html',
  styleUrls: ['./make-transaction.component.scss']
})
export class MakeTransactionComponent implements OnInit, OnDestroy {

  transactionRequestForm = new FormGroup({
    amount: new FormControl('', [Validators.required ]),
    otherSide: new FormControl('', [Validators.required]),
  });

  transactionSubscription: Subscription;
  authSubscription: Subscription;

  loggedUser: User;

  constructor(private transactionService: TransactionService,
              private authService: AuthService,
              private toast: ToastrService
            ) { }

  ngOnInit(): void {
    this.authSubscription = this.authService.getSubjectCurrentUser().subscribe(
      res => {
        if (res) {
          this.loggedUser = res;
        }
      }
    );
  }

  createPayment(): void {
    if (this.transactionRequestForm.valid) {
      const request: TransactionRequest = {
        clientId: this.loggedUser.id, 
        amount: +this.transactionRequestForm.get('amount').value,
        otherSide: this.transactionRequestForm.get('otherSide').value
      }
      console.log(request)
      this.transactionSubscription = this.transactionService.createTransaction(request).subscribe(
        res => {
          this.toast.success('Transaction is successfully created!', 'Success')
        },
        err => {
          this.toast.error('Transaction creation failed, check your balance', 'Failure')
        }
      );
    }
  }

  ngOnDestroy(): void {
      if (this.transactionSubscription) {
        this.transactionSubscription.unsubscribe();
      }

      if (this.authSubscription) {
        this.authSubscription.unsubscribe();
      }
  }
  
}
