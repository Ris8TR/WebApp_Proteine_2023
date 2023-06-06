import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {ProductService} from "../../Service/product.service";
import {Router} from "@angular/router";
import {NavigationService} from "../../Service/navigation.service";
import {HttpClient} from "@angular/common/http";
import {CartService} from "../../Service/cart.service";


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  product: any[] = [];
  ordineCreato=false;
  logStringResult: any;
  logStringResultBool=false;
  constructor(private cookieService: CookieService,
              private productService: ProductService,
              private navigationService: NavigationService,
              private cartService: CartService,
              private http: HttpClient,
              private router: Router) {}

  ngOnInit(): void {
    this.checkUserCookieBool()
    const cartItemsCookie = this.cookieService.get('cartItems');
    if (cartItemsCookie) {
      this.product = JSON.parse(cartItemsCookie);
    } else {
      this.product = [];
    }
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
          this.setProductImageSrc(cartItem.product.foto, cartItem);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  showProductDetails(productId){
    const currentRoute = this.router.url;
    this.navigationService.setPreviousComponent(currentRoute);
    this.router.navigate(['/product',  productId ]);
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
    // Elimina il cookie precedente per i carrelli
    this.cookieService.delete('cartItems', '/');

    // Crea un nuovo array di oggetti con ID prodotto e quantità
    const updatedCartItems = cartItems.map(item => ({ product_id: item.product_id, quantity: item.quantity }));

    // Salva il nuovo array di oggetti come cookie
    this.cookieService.set('cartItems', JSON.stringify(updatedCartItems), 1, '/');
  }

  createOrder(): void {
    const userCookie = this.cookieService.get('user');

    if (userCookie) {
      const orderItems = this.product.map(item => ({ product_id: item.product_id, quantity: item.quantity }));
      const order = {
        user: userCookie,
        items: orderItems
      };

      this.cartService.sendOrder(order).subscribe(
        (response) => {
          console.log('Order created:', response);
          // Clear the cart items after successful order creation
          this.product = [];
          this.saveCartItems([]);
          this.ordineCreato = true;
        },
        (error) => {
          this.ordineCreato = false;
          console.log(order);
          console.error('Error creating order:', error);
        }
      );
    } else {
      console.log('User cookie not found');
    }
  }


  checkUserCookieBool(): void {
    const userCookie = this.cookieService.get('user');

    if (userCookie) {
      this.logStringResultBool = true;
    } else {
      this.logStringResultBool = false;
    }
  }


}

