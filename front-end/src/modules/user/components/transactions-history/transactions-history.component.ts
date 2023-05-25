import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Transaction } from 'src/modules/shared/model/transaction';
import { TransactionService } from 'src/modules/shared/service/transaction-service/transaction.service';

@Component({
  selector: 'app-transactions-history',
  templateUrl: './transactions-history.component.html',
  styleUrls: ['./transactions-history.component.scss']
})
export class TransactionsHistoryComponent implements OnInit, OnDestroy {

  @Input() accountId: number;

  transactions: Transaction[];
  transactionSubscription: Subscription;
  parameter: string = 'ACTIVE';

  filterByPossibilities = [
    { name: 'Active', checked: true, value: 'ACTIVE' },
    { name: 'Pending', checked: false, value: 'PENDING' },
    { name: 'Rejected', checked: false, value: 'REJECTED' },
  ];

  constructor(private transactionService: TransactionService) {
    this.transactions = [];
  }

  ngOnInit(): void {
    if (this.accountId) {
      this.loadData();
    }
  }

  loadData(): void {
    this.transactionSubscription = this.transactionService.getFilteredTransactions(this.accountId, this.parameter).subscribe(
        res => {
          if (res) {
            this.transactions = res;
            console.log(this.transactions);
          }
        }
      )
  }

  changeFilter(parameter: string): void {
    this.parameter = parameter;
    this.loadData();
  }

  ngOnDestroy(): void {
      if (this.transactionSubscription) {
        this.transactionSubscription.unsubscribe();
      }
  }

}
