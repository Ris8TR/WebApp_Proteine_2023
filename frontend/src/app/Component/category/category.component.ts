import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {ActivatedRoute, Router} from "@angular/router";

import {CartService} from "../../Service/cart.service";
import {tap} from "rxjs/operators";
import {Product} from "../../Model/Product.model";
import {NavigationService} from "../../Service/navigation.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit{

  Category: string | undefined;
  pageNumber: number = 0;
  product: any[] = [];
  disableLoadMore
  productAddedToCart: number | null = null;
  constructor(private productService: ProductService,
              private http: HttpClient,
              private cookieService: CookieService,
              private cartService: CartService,
              private route: ActivatedRoute,
              private navigationService: NavigationService,
              private router : Router) { }



  public getProductByCategory(pageNumber: number) {
    this.pageNumber = pageNumber;
    this.productService.getProductByCategory(pageNumber, this.Category)
      .pipe(
        tap((resp: Product[]) => {
          console.log(resp);
          this.disableLoadMore = resp.length == 8;
          resp.forEach((p: Product) => {
            p.imageUrl = this.setProductImageSrc(p.foto);
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

  public getmoreProductByCategory(pageNumber: number) {
    this.pageNumber = pageNumber;
    this.productService.getProductByCategory(pageNumber, this.Category)
      .pipe(
        tap((resp: Product[]) => {
          this.disableLoadMore = resp.length == 8;
          resp.forEach((p: Product) => {
            p.imageUrl = this.setProductImageSrc(p.foto);
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

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.Category = String(params.get('categoryid'));
      this.pageNumber = 0;
      this.product = []; // Reset dell'array product
      this.getProductByCategory(this.pageNumber);
    });
  }



  getCategory(){
    return this.Category;
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


  public loadMoreProduct(){
    this.pageNumber = this.pageNumber+1;
    this.getmoreProductByCategory(this.pageNumber);
  }

  showProductDetails(productId){
    const currentRoute = this.router.url;
    this.navigationService.setPreviousComponent(currentRoute);
    this.router.navigate(['/product',  productId ]);
  }


  addToCart(ID){
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
  }

}
