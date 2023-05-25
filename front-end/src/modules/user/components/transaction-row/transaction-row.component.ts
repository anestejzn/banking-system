import { Component, Input, OnInit } from '@angular/core';
import { Transaction } from 'src/modules/shared/model/transaction';

@Component({
  selector: 'app-transaction-row',
  templateUrl: './transaction-row.component.html',
  styleUrls: ['./transaction-row.component.scss']
})
export class TransactionRowComponent implements OnInit {
  @Input() index: number;
  @Input() transaction: Transaction;

  constructor() { }

  ngOnInit(): void {
  }

}
