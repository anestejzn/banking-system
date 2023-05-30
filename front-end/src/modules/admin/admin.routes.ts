import { Routes } from "@angular/router";
import { HomeComponent } from "./pages/home/home.component";
import { ChartGraphComponent } from "./components/chart-graph/chart-graph.component";
import { ChartsPageComponent } from "./pages/charts-page/charts-page.component";

export const AdminRoutes: Routes = [
    {
      path: "home",
      pathMatch: "full",
      component: HomeComponent
    },
    {
      path: "reports",
      pathMatch: "full",
      component: ChartsPageComponent
    },
]