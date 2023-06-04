import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartComponent } from './Component/cart/cart.component';
import { HomeComponent } from './Component/home/home.component';
import {FormsModule} from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCardModule} from "@angular/material/card";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'
import { ReactiveFormsModule } from '@angular/forms';
import { ProductComponent } from './Component/product/product.component';
import { HttpClientModule} from "@angular/common/http";
import { HeaderComponent } from './Component/header/header.component';
import { CookieService } from 'ngx-cookie-service';
import { CategoryComponent } from './Component/category/category.component';
import { SearchComponent } from './Component/search/search.component';
import { InfoComponent } from './Component/info/info.component';
import { FacebookModule } from 'ngx-facebook';







@NgModule({
  declarations: [
    AppComponent,
    CartComponent,
    HomeComponent,
    ProductComponent,
    HeaderComponent,
    CategoryComponent,
    SearchComponent,
    InfoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FacebookModule.forRoot(),

  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})

export class AppModule { }
