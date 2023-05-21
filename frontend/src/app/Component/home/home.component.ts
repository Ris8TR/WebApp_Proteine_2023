import { Component, OnInit  } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {ProductService} from "../../Service/product.service";
import {Router} from "@angular/router";
import {ImageProcessingService} from "../../Service/image-processing.service";
import {Product} from "../../Model/Product.model";
import { map } from 'rxjs/operators';
import { tap } from 'rxjs/operators';



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{


  pageNumber: number = 0;
  productDetails  =[ [] as any];
  showLoadButton = false;
  constructor(private productService: ProductService,
              private imageProcessingService: ImageProcessingService,
              private http: HttpClient,
              private router : Router) { }

  ngOnInit(): void {
    this.getAllProducts();
  }


  /*
  public getAllProducts(searchKey: string =""){
    this.productService.getAllProducts(this.pageNumber, searchKey)
      .pipe(
        map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
      )
      .subscribe(
        (resp: Product[]) =>{
          console.log(resp);
          if(resp.length == 8){
            this.showLoadButton = true;
          }else{this.showLoadButton = false}
          resp.forEach(p => this.productDetails.push(p));
          // this.productDetails = resp;
        }, (error: HttpErrorResponse) => {
          console.log(error);
        }

      );
  }
*/

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



}
