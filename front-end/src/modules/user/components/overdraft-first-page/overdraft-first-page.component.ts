import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-overdraft-first-page',
  templateUrl: './overdraft-first-page.component.html',
  styleUrls: ['./overdraft-first-page.component.scss']
})
export class OverdraftFirstPageComponent implements OnInit {
  @Output() overdraftRequest = new EventEmitter();

  overdraftRequestForm = new FormGroup({
    firstMonthlyIncome: new FormControl('', [Validators.required ]),
    secondMonthlyIncome: new FormControl('', [Validators.required]),
    thirdMonthlyIncome: new FormControl('', [Validators.required]),
  });

  constructor() { }

  ngOnInit(): void {
  }

  showMessage(){
    const firstMonthlyIncome = this.overdraftRequestForm.get('firstMonthlyIncome').value;
    const secondMonthlyIncome = this.overdraftRequestForm.get('secondMonthlyIncome').value;
    const thirdMonthlyIncome = this.overdraftRequestForm.get('thirdMonthlyIncome').value;

  
    return firstMonthlyIncome !== '' && secondMonthlyIncome !== '' && thirdMonthlyIncome !== '';
  }
  calculateAmount(){
    const firstMonthlyIncome = +this.overdraftRequestForm.get('firstMonthlyIncome').value;
    const secondMonthlyIncome = +this.overdraftRequestForm.get('secondMonthlyIncome').value;
    const thirdMonthlyIncome = +this.overdraftRequestForm.get('thirdMonthlyIncome').value;
    const sumOfIncome = firstMonthlyIncome + secondMonthlyIncome + thirdMonthlyIncome;

    return (sumOfIncome / 3).toFixed(2);
  }

  send(){
    this.overdraftRequest.emit(
      {
        firstMonthlyIncome: +this.overdraftRequestForm.get('firstMonthlyIncome').value,
        secondMonthlyIncome: +this.overdraftRequestForm.get('secondMonthlyIncome').value,
        thirdMonthlyIncome: +this.overdraftRequestForm.get('thirdMonthlyIncome').value
      }
    );
  }



}
