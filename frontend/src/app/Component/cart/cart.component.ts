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
  product: any[] = [];

  constructor(private cookieService: CookieService, private productService: ProductService) {}

  ngOnInit(): void {
    // Leggi l'elenco dei prodotti dal cookie

    const cartItemsCookie = this.cookieService.get('cartItems');
    if (cartItemsCookie) {
      this.product = JSON.parse(cartItemsCookie);
    } else {
      this.product = [];
    }

    // Carica i dettagli dei prodotti solo quando il carrello viene inizialmente caricato
    this.loadProductDetails();
  }

  loadProductDetails(): void {
    for (const cartItem of this.product) {
      this.getProductById(cartItem.product_id);
    }
  }



  getProductById(productId: number): void {
    this.productService.getProductById(productId).subscribe(
      (data: any) => {
        const cartItem = this.product.find(item => item.product_id === productId);
        if (cartItem) {
          cartItem.product = data;
          this.setProductImageSrc(cartItem.product.val_nutr, cartItem);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  setProductImageSrc(base64Image: string, cartItem: any): void {
    if (!base64Image) {
      cartItem.product.imageUrl = '/./assets/images/logo.png';
      return;
    }

    const cleanedBase64Image = base64Image.replace(/\s/g, '');
    const imageBlob = this.base64ToBlob(cleanedBase64Image);
    cartItem.product.imageUrl = URL.createObjectURL(imageBlob);
  }

  base64ToBlob(base64Data: string): Blob {
    const byteString = atob(base64Data);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const uint8Array = new Uint8Array(arrayBuffer);

    for (let i = 0; i < byteString.length; i++) {
      uint8Array[i] = byteString.charCodeAt(i);
    }

    const blob = new Blob([arrayBuffer], { type: 'image/jpeg' }); // Assumendo che l'immagine sia in formato JPEG

    return blob;
  }



  decreaseQuantity(index: number): void {
  if (this.product[index].quantity > 1) {
  this.product[index].quantity--;
  this.saveCartItems(this.product);
}
}

increaseQuantity(index: number): void {
  this.product[index].quantity++;
  this.saveCartItems(this.product);
}

removeFromCart(index: number): void {
  this.product.splice(index, 1);
  this.saveCartItems(this.product);
}

private saveCartItems(cartItems: { product_id: number, quantity: number }[]): void {
  this.cookieService.set('cartItems', JSON.stringify(cartItems), 1, '/');
}



}

