import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Debit } from 'src/modules/shared/model/debit';
import { DebitService } from 'src/modules/shared/service/debit-service/debit.service';

@Component({
  selector: 'app-overdraft-second-page',
  templateUrl: './overdraft-second-page.component.html',
  styleUrls: ['./overdraft-second-page.component.scss']
})
export class OverdraftSecondPageComponent implements OnInit {

  @Output() answer = new EventEmitter<string>();
  @Input() debit: Debit;
  processedDebit: Debit;
  debitSubscription: Subscription;
  
  showQuestionAndButtons = true;

  constructor(private debitService: DebitService, private toast: ToastrService) { }

  ngOnInit(): void {
    console.log(this.debit);

    this.processedDebit = this.debit;
  }

  answerSecondPage(ans: string){
    if(ans === 'yes'){
      this.showQuestionAndButtons = false;
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
