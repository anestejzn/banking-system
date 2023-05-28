import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-credit-card-details',
  templateUrl: './credit-card-details.component.html',
  styleUrls: ['./credit-card-details.component.scss']
})
export class CreditCardDetailsComponent implements OnInit {

  @Input() selectedCard: string;

  constructor() { }

  ngOnInit(): void {
  }

}
