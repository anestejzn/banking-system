import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Debit } from 'src/modules/shared/model/debit';

@Component({
  selector: 'app-cash-credit-second-page',
  templateUrl: './cash-credit-second-page.component.html',
  styleUrls: ['./cash-credit-second-page.component.scss']
})
export class CashCreditSecondPageComponent implements OnInit {
  @Output() answer = new EventEmitter<string>();
  @Input() debit: Debit;
  processedDebit: Debit;
  
  showQuestionAndButtons = true;

  constructor() { }

  ngOnInit(): void {
    console.log(this.debit);

    this.processedDebit = this.debit;
  }

  answerSecondPage(ans: string){
    if(ans === 'yes'){
      this.showQuestionAndButtons = false;
    }
    this.answer.emit(ans);
  }

}
