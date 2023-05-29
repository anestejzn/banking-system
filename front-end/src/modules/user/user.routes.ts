import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { CashCreditRequestComponent } from "./pages/cash-credit-request/cash-credit-request.component";
import { CreditCardRequestPageComponent } from "./pages/credit-card-request-page/credit-card-request-page.component";
import { OverdraftRequestComponent } from "./pages/overdraft-request/overdraft-request.component";

export const UserRoutes: Routes = [
    {
      path: "home",
      pathMatch: "full",
      component: HomeComponent
    },
    {
      path: "cash-credit-requests",
      pathMatch: "full",
      component: CashCreditRequestComponent
    },
    {
      path: "credit-card-request",
      pathMatch: "full",
      component: CreditCardRequestPageComponent
    },
    {
      path: "overdraft-requests",
      pathMatch: "full",
      component: OverdraftRequestComponent
    }
]