import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {ProductService} from "../../Service/product.service";
import {Product} from "../../Model/Product.model";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: any[] = [];

  constructor(private cookieService: CookieService, private productService: ProductService) {}

  ngOnInit(): void {
    // Leggi l'elenco dei prodotti dal cookie

    const cartItemsCookie = this.cookieService.get('cartItems');
    if (cartItemsCookie) {
      this.cartItems = JSON.parse(cartItemsCookie);
    } else {
      this.cartItems = [];
    }

    // Carica i dettagli dei prodotti solo quando il carrello viene inizialmente caricato
    this.loadProductDetails();
  }

  loadProductDetails(): void {
    for (const cartItem of this.cartItems) {
      this.getProductById(cartItem.product_id);
    }
  }

  getProductById(productId: number): void {
    this.productService.getProductById(productId).subscribe(
      (data: any) => {
        const cartItem = this.cartItems.find(item => item.product_id === productId);
        if (cartItem) {
          cartItem.product = data;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

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
this.saveCartItems(cartItems);
}

decreaseQuantity(index: number): void {
  if (this.cartItems[index].quantity > 1) {
  this.cartItems[index].quantity--;
  this.saveCartItems(this.cartItems);
}
}

increaseQuantity(index: number): void {
  this.cartItems[index].quantity++;
  this.saveCartItems(this.cartItems);
}

removeFromCart(index: number): void {
  this.cartItems.splice(index, 1);
  this.saveCartItems(this.cartItems);
}

private saveCartItems(cartItems: { product_id: number, quantity: number }[]): void {
  this.cookieService.set('cartItems', JSON.stringify(cartItems), 1, '/');
}



}

