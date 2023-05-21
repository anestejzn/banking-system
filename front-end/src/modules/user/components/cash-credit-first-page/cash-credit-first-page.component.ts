import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cash-credit-first-page',
  templateUrl: './cash-credit-first-page.component.html',
  styleUrls: ['./cash-credit-first-page.component.scss']
})
export class CashCreditFirstPageComponent implements OnInit {
  @Output() sendRequest = new EventEmitter<{amount:number, paymentPeriod:number}>();

  cashCreditRequestForm = new FormGroup({
    amount: new FormControl('', [Validators.required ]),
    paymentPeriod: new FormControl('', [
      Validators.required
    ]),
  });

  constructor() { }

  ngOnInit(): void {
  }

  sendRequestCashCredit(){
    this.sendRequest.emit(
      {amount: +this.cashCreditRequestForm.get('amount').value,
       paymentPeriod:  +this.cashCreditRequestForm.get('paymentPeriod').value});
  }

}
