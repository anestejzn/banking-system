import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { Account } from 'src/modules/shared/model/account';
import { Client, User } from 'src/modules/shared/model/user';
import { AccountService } from 'src/modules/shared/service/account.service';
import { ClientService } from 'src/modules/shared/service/client-service/client.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  authSubscription: Subscription;
  clientSubscription: Subscription;
  accountSubscription: Subscription;
  account: Account;
  loggedUser: User;
  client: Client;


  constructor(private authService: AuthService,
              private clientService: ClientService,
              private accountService: AccountService     
  ) { }

  ngOnInit(): void {

    this.authSubscription = this.authService
      .getSubjectCurrentUser()
      .subscribe(user => {
        if (user) {
          this.loggedUser = user;
          this.loadClientData();
        }
      });
  }

  loadClientData(): void {
    this.clientSubscription = this.clientService.getClientById(this.loggedUser.id).subscribe(
      res => {
        this.client = res;
        this.loadAccountData();
      }
    )
  }

  loadAccountData(): void {
    this.accountSubscription = this.accountService.getAccountById(this.client.accountId).subscribe(
      res => {
        this.account = res;
      }
    )
  }

  ngOnDestroy(): void {
   if (this.authSubscription) {
    this.authSubscription.unsubscribe();
   }   

   if (this.clientSubscription) {
    this.clientSubscription.unsubscribe();
   }

   if (this.accountSubscription) {
    this.accountSubscription.unsubscribe();
   }

  }

}
