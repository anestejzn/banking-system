import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-credit-card-request-page',
  templateUrl: './credit-card-request-page.component.html',
  styleUrls: ['./credit-card-request-page.component.scss']
})
export class CreditCardRequestPageComponent implements OnInit {

  selected: string = 'visa';

  constructor() { }

  ngOnInit(): void {
  }

  changeSelected(cardName: string): void {
    this.selected = cardName;
  }

}
