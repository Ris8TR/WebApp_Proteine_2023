import { Component, OnInit  } from '@angular/core';
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
export class HomeComponent implements OnInit{


  pageNumber: number = 0;
  productDetails: any[] = [];
  showLoadButton = false;
  productAddedToCart: number | null = null;
  constructor(private productService: ProductService,
              private imageProcessingService: ImageProcessingService,
              private http: HttpClient,
              private cartService: CartService,
              private cookieService: CookieService,
              private router : Router) { }

  ngOnInit(): void {
    this.getAllProducts();
  }



getProductById(id: number) {
  const url = `/getProductsById?id=${id}`;
  return this.http.get<Product>(url);
}



public getAllProducts(searchKey: string = "") {
  this.productService.getAllProducts(this.pageNumber, searchKey)
    .pipe(
      tap((resp: Product[]) => {
        console.log(resp);
        this.showLoadButton = resp.length == 8;
        resp.forEach(p => this.productDetails.push(p));
        // this.productDetails = resp;
      })
    )
    .subscribe(
      () => {},
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
}


public loadMoreProduct(){
    this.pageNumber = this.pageNumber+1;
    this.getAllProducts();
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
