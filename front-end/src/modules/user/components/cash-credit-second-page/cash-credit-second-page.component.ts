import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Debit } from 'src/modules/shared/model/debit';
import { DebitService } from 'src/modules/shared/service/debit-service/debit.service';

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
  debitSubscription: Subscription;

  constructor(private debitService: DebitService, private toast: ToastrService) { }

  ngOnInit(): void {
    console.log(this.debit);

    this.processedDebit = this.debit;
  }

  answerSecondPage(ans: string){
    if(ans === 'yes'){
      this.showQuestionAndButtons = false;
      console.log("lalal");
      this.debitSubscription = this.debitService.acceptDebitRequest(this.processedDebit.id).subscribe(
        response => {
          this.toast.info("Amount is on your account.", "Info");
        },
        error => {
          this.toast.error(error.error, "Error happened");
        }
      )
    }
    this.answer.emit(ans);
  }

}
