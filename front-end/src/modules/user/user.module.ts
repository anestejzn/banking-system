import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UserRoutes } from './user.routes';
import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './pages/home/home.component';
import { CashCreditRequestComponent } from './pages/cash-credit-request/cash-credit-request.component';
import { CashCreditFirstPageComponent } from './components/cash-credit-first-page/cash-credit-first-page.component';
import { CashCreditSecondPageComponent } from './components/cash-credit-second-page/cash-credit-second-page.component';
import { AccountInfoComponent } from './components/account-info/account-info.component';
import { CardsInfoComponent } from './components/cards-info/cards-info.component';
import { TransactionsHistoryComponent } from './components/transactions-history/transactions-history.component';
import { TransactionRowComponent } from './components/transaction-row/transaction-row.component';
import { CreditCardRequestPageComponent } from './pages/credit-card-request-page/credit-card-request-page.component';
import { CreditCardDetailsComponent } from './components/credit-card-details/credit-card-details.component';
import { OverdraftRequestComponent } from './pages/overdraft-request/overdraft-request.component';
import { OverdraftFirstPageComponent } from './components/overdraft-first-page/overdraft-first-page.component';
import { OverdraftSecondPageComponent } from './components/overdraft-second-page/overdraft-second-page.component';
import { MakeTransactionComponent } from './pages/make-transaction/make-transaction.component';


@NgModule({
  declarations: [
   HomeComponent,
   CashCreditRequestComponent,
   CashCreditFirstPageComponent,
   CashCreditSecondPageComponent,
   AccountInfoComponent,
   CardsInfoComponent,
   TransactionsHistoryComponent,
   TransactionRowComponent,
   CreditCardRequestPageComponent,
   CreditCardDetailsComponent,
   OverdraftRequestComponent,
   OverdraftFirstPageComponent,
   OverdraftSecondPageComponent,
   MakeTransactionComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(UserRoutes)
  ],
  exports:[
    HomeComponent
  ]
})
export class UserModule { }
