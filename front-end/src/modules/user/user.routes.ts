import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { CashCreditRequestComponent } from "./pages/cash-credit-request/cash-credit-request.component";

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
    }
]