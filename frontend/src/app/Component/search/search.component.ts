import { Component } from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {CartService} from "../../Service/cart.service";
import {ActivatedRoute, Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {Product} from "../../Model/Product.model";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {


  search: string | undefined;
  pageNumber: number = 0;
  product: any[] = [];
  showLoadButton = false;
  productAddedToCart: number | null = null;
  constructor(private productService: ProductService,
              private http: HttpClient,
              private cookieService: CookieService,
              private cartService: CartService,
              private route: ActivatedRoute,
              private router : Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.search = String(params.get('searchstring'));
      this.getProductsBySearch();
    });
  }


  getProductsBySearch() {
    this.product = []; // Reset dell'array product
    this.productService.getProductsBySearch(this.search)
      .pipe(
        tap((resp: Product[]) => {
          console.log(resp);
          this.showLoadButton = resp.length == 8;
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
    this.getProductsBySearch();
  }

  showProductDetails(productId){
    this.router.navigate(['/product' , {productId: productId}]);
  }


  addToCart(ID){
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
  }




}
