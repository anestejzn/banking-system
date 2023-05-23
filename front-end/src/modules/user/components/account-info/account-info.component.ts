import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'src/modules/shared/model/account';

@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
  styleUrls: ['./account-info.component.scss']
})
export class AccountInfoComponent implements OnInit {
  @Input() account: Account;

  constructor() { }

  ngOnInit(): void {
  }

}
