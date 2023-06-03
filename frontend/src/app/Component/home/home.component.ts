import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {ProductService} from "../../Service/product.service";
import {Router} from "@angular/router";
import {ImageProcessingService} from "../../Service/image-processing.service";
import {Product} from "../../Model/Product.model";
import { tap } from 'rxjs/operators';
import {CookieService} from "ngx-cookie-service";
import {CartService} from "../../Service/cart.service";



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  pageNumber: number = 0;
  product: any[] = [];
  showLoadButton = false;
  productAddedToCart: number | null = null;

  constructor(
    private productService: ProductService,
    private imageProcessingService: ImageProcessingService,
    private http: HttpClient,
    private cartService: CartService,
    private cookieService: CookieService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllProducts();
  }

  getProductById(id: number) {
    const url = `/getProductsById?id=${id}`;
    return this.http.get<Product>(url);
  }

  public getAllProducts(searchKey: string = "") {
    this.pageNumber = 0;
    this.product = [];
    this.loadProducts();
  }

  private loadProducts() {
    this.productService
      .getAllProducts(this.pageNumber)
      .pipe(
        tap((resp: Product[]) => {
          console.log(resp);
          this.showLoadButton = resp.length == 10;
          resp.forEach((p: Product) => {
            p.imageUrl = this.setProductImageSrc(p.val_nutr);
            this.product.push(p);
          });
        })
      )
      .subscribe(
        () => {},
        (error: HttpErrorResponse) => {
          console.log(error);
        }
      );
  }



  setProductImageSrc(base64Image: string): string {
    if (!base64Image) {
      return '/./assets/images/logo.png';
    }

    const cleanedBase64Image = base64Image.replace(/\s/g, '');
    const imageBlob = this.base64ToBlob(cleanedBase64Image);
    return URL.createObjectURL(imageBlob);
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

  public loadMoreProduct() {
    this.pageNumber++;
    this.loadProducts();
  }

  showProductDetails(productId) {
    this.router.navigate(['/product', { productId: productId }]);
  }

  addToCart(ID) {
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
  }

  ngOnDestroy(): void {
    this.product =[];
  }
}
