import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
import { User } from 'src/modules/shared/model/user';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  loggedUser:User;
  authSubscription: Subscription;
  isAdmin: boolean = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    
    this.authSubscription = this.authService
      .getSubjectCurrentUser()
      .subscribe(user => {
        this.loggedUser = user;
        this.isAdmin = this.loggedUser.role.roleName === 'ROLE_ADMIN';
      });
  }

  logOut(){
    this.authService.logOut().subscribe();
    sessionStorage.clear();
    this.router.navigate(['/banking-system/auth/login'])
  }

  redirectToCashCreditRequestsPage() {
    this.router.navigate(['/banking-system/user/cash-credit-requests']);
  }

  redirectToCardRequestsPage() {
    this.router.navigate([`/serb-uber/user/user-profile/${this.loggedUser.id}`]);
  }

  redirectToOverdraftRequestsPage() {
    this.router.navigate(['/serb-uber/admin/messages']);
  }

}
