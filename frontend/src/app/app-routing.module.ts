import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./Component/home/home.component";
import {ReactiveFormsModule} from "@angular/forms";
import {CartComponent} from "./Component/cart/cart.component";
import {ProductComponent} from "./Component/product/product.component";
import {CategoryComponent} from "./Component/category/category.component";


const routes: Routes = [
  {path: '', redirectTo: "home", pathMatch : "full"},
  {path: 'home', component: HomeComponent},
  {path: 'cart', component: CartComponent},
  {path: 'product/:id', component: ProductComponent},
  {path: 'category/:categoryid', component: CategoryComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    ReactiveFormsModule,
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
