import { Injectable } from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {ProductService} from "./product.service";

@Injectable({
  providedIn: 'root'
})
export class CartService {


  constructor( private cookieService: CookieService,private productService: ProductService) { }


  addToCart(productId: number): void {
    // Leggi l'elenco dei prodotti nel carrello dai cookie
    let cartItems: { product_id: number, quantity: number }[] = [];
    const cartItemsCookie = this.cookieService.get('cartItems');

    if (cartItemsCookie) {
      cartItems = JSON.parse(cartItemsCookie) as { product_id: number, quantity: number }[];
    }

    // Verifica se il prodotto è già presente nel carrello
    const existingProductIndex = cartItems.findIndex(item => item.product_id === productId);

    if (existingProductIndex !== -1) {
      // Se il prodotto è già presente, incrementa la quantità
      cartItems[existingProductIndex].quantity += 1;
    } else {
      // Se il prodotto non è presente, aggiungi una nuova voce al carrello
      cartItems.push({ product_id: productId, quantity: 1 });
    }

    // Salva l'elenco dei prodotti nel carrello nei cookie con una scadenza di un'ora
    this.cookieService.set('cartItems', JSON.stringify(cartItems), 1, '/');

  }
}
