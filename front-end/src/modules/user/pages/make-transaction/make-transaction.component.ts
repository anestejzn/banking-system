import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-make-transaction',
  templateUrl: './make-transaction.component.html',
  styleUrls: ['./make-transaction.component.scss']
})
export class MakeTransactionComponent implements OnInit {

  transactionRequestForm = new FormGroup({
    amount: new FormControl('', [Validators.required ]),
    otherSide: new FormControl('', [Validators.required]),
  });

  constructor() { }

  ngOnInit(): void {
  }

  createPayment(): void {
    
  }
  
}
