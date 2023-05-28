import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { CreditCardRequest } from 'src/modules/shared/model/credit-card-request';
import { User } from 'src/modules/shared/model/user';
import { CreditCardService } from 'src/modules/shared/service/credit-card-service/credit-card.service';

@Component({
  selector: 'app-credit-card-request-page',
  templateUrl: './credit-card-request-page.component.html',
  styleUrls: ['./credit-card-request-page.component.scss']
})
export class CreditCardRequestPageComponent implements OnInit, OnDestroy {

  selected: string = 'visa';
  loggedUser: User = null;
  authSubscription: Subscription;
  creditCardSubscription: Subscription;

  constructor(private authService: AuthService, 
              private creditCardService: CreditCardService,
              private toast: ToastrService) { }

  ngOnInit(): void {
    this.authSubscription = this.authService.getSubjectCurrentUser().subscribe(
      res => {
        if (res) {
          this.loggedUser = res;
        }
      }
    )
  }

  sendRequest(): void {
    const request: CreditCardRequest = {clientId: this.loggedUser.id, creditCardName: this.selected}
    this.creditCardSubscription = this.creditCardService.addNewCreditCard(request).subscribe(
      res => {
        if (res) {
          this.toast.success("You successfully bought a new card!", "Success")
        } else {
          this.toast.error("Error happened, please check in bank documentation to see if you are eligable!", "Failure")
        }
      },
      err => {
        this.toast.error("Error happened, please check in bank documentation to see if you are eligable!", "Failure")
      }
    )

  }

  changeSelected(cardName: string): void {
    this.selected = cardName;
  }

  ngOnDestroy(): void {
      if (this.authSubscription) {
        this.authSubscription.unsubscribe();
      }

      if (this.creditCardSubscription) {
        this.creditCardSubscription.unsubscribe();
      }
  }

}
