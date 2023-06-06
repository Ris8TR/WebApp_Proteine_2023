import { Injectable } from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {ProductService} from "./product.service";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CartService {


  constructor( private cookieService: CookieService,
               private httpClient: HttpClient) { }

  public sendOrder(order: { user: string; items: { quantity: any; product_id: any }[] }): Observable<any> {
    return this.httpClient.post('http://localhost:8080/checkout', order);
  }
  addToCart(productId: number): void {

    let cartItems: { product_id: number, quantity: number }[] = [];
    const cartItemsCookie = this.cookieService.get('cartItems');
    if (cartItemsCookie) {
      cartItems = JSON.parse(cartItemsCookie) as { product_id: number, quantity: number }[];
    }
    const existingProductIndex = cartItems.findIndex(item => item.product_id === productId);
    if (existingProductIndex !== -1) {
      cartItems[existingProductIndex].quantity += 1;
    } else {
      cartItems.push({ product_id: productId, quantity: 1 });
    }
    this.cookieService.set('cartItems', JSON.stringify(cartItems), 1, '/');

  }


}
