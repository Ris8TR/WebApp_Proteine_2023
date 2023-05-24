import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {ImageProcessingService} from "../../Service/image-processing.service";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {ActivatedRoute, Router} from "@angular/router";

import {CartService} from "../../Service/cart.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit{

  Category: string | undefined;
  pageNumber: number = 0;
  product: any[] = [];
  showLoadButton = false;
  productAddedToCart: number | null = null;
  constructor(private productService: ProductService,
              private imageProcessingService: ImageProcessingService,
              private http: HttpClient,
              private cookieService: CookieService,
              private cartService: CartService,
              private route: ActivatedRoute,
              private router : Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
    this.Category = String(params.get('categoryid'));
    this.getProductByCategory();
  });
  }

  getCategory(){
    return this.Category;
  }



  getProductByCategory() {
    this.productService.getProductByCategory(this.Category).subscribe(
      (data: any) => {
        this.product = data;
        console.log(this.product);
      },
      (error) => {
        console.log(error);
      }
    );
  }


  public loadMoreProduct(){
    this.pageNumber = this.pageNumber+1;
    this.getProductByCategory();
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
