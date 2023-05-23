import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-cards-info',
  templateUrl: './cards-info.component.html',
  styleUrls: ['./cards-info.component.scss']
})
export class CardsInfoComponent implements OnInit {

  @Input() accountCards: string[];
  @Input() additionalCards: string[];

  constructor() {
    this.accountCards = [];
    this.additionalCards = [];
  }

  ngOnInit(): void {
  }

}
