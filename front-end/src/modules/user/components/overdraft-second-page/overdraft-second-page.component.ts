import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Debit } from 'src/modules/shared/model/debit';

@Component({
  selector: 'app-overdraft-second-page',
  templateUrl: './overdraft-second-page.component.html',
  styleUrls: ['./overdraft-second-page.component.scss']
})
export class OverdraftSecondPageComponent implements OnInit {

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
