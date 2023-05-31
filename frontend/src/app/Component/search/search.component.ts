import { Component } from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {ImageProcessingService} from "../../Service/image-processing.service";
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {CartService} from "../../Service/cart.service";
import {ActivatedRoute, Router} from "@angular/router";

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
              private imageProcessingService: ImageProcessingService,
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
    this.productService.getProductsBySearch(this.search).subscribe(
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
