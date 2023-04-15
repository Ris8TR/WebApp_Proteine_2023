import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./Component/home/home.component";
import {LoginComponent} from "./Component/login/login.component";
import {UserComponent} from "./Component/user/user.component";
import {ReactiveFormsModule} from "@angular/forms";
import {CartComponent} from "./Component/cart/cart.component";
import {ProductComponent} from "./Component/product/product.component";


const routes: Routes = [
  {path: '', redirectTo: "home", pathMatch : "full"},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'user', component: UserComponent},
  {path: 'cart', component: CartComponent},
  {path: 'product', component: ProductComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    ReactiveFormsModule,
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
