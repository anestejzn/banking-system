import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RootLayoutComponent } from './components/root-layout/root-layout.component';

const routes: Routes = [
  {
    path: "banking-system",
    component: RootLayoutComponent,
    children: [
      {
        path: "auth",
        loadChildren: () => 
          import("./../auth/auth.module").then((m) => m.AuthModule)
      },
      {
        path: "admin",
        loadChildren: () => 
          import("./../admin/admin.module").then((m) => m.AdminModule)
      },
      {
        path: "user",
        loadChildren: () => 
          import("./../user/user.module").then((m) => m.UserModule)
      },
    ],
  },
  {
    path: "",
    redirectTo: "/banking-system/auth/login",
    pathMatch: "full",
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
