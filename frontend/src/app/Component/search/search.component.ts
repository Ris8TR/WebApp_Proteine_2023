import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {CartService} from "../../Service/cart.service";
import {ActivatedRoute, Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {Product} from "../../Model/Product.model";
import {NavigationService} from "../../Service/navigation.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{


  search: string | undefined;
  pageNumber: number = 0;
  product: any[] = [];
  disableLoadMore= false;
  productAddedToCart: number | null = null;
  constructor(private productService: ProductService,
              private http: HttpClient,
              private cookieService: CookieService,
              private cartService: CartService,
              private route: ActivatedRoute,
              private navigationService: NavigationService,
              private router : Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.search = String(params.get('searchstring'));
      this.getProductsBySearch(0);
    });
  }


  public getProductsBySearch(pageNumber: number) {
    this.product = []; // Reset dell'array product
    if (!this.search || this.search.trim() === '') {
      return; // Non eseguire la chiamata API se la barra di ricerca è vuota
    }
    this.productService.getProductsBySearch(pageNumber, this.search)
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

  public getmoreProductsBySearch(pageNumber: number) {

    this.productService.getProductsBySearch(pageNumber, this.search)
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
    this.getmoreProductsBySearch(this.pageNumber);
  }





}
