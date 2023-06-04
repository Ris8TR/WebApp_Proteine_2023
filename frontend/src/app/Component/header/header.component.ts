import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {Product} from "../../Model/Product.model";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  pageNumber: number = 0;
  logStringResult: string | undefined;
  productDetails = [[] as any];
  showLoadButton = false;

  constructor(private productService: ProductService,
              private router: Router,
              private cookieService: CookieService){}



  ngOnInit(): void {
    this.checkUserCookie()
  }


  redirectToSearch(searchKeyword: string) {

    const formattedSearch = searchKeyword.toLowerCase();
    this.router.navigate(['/search', formattedSearch]);

  }

  checkUserCookie(): void {
    const userCookie = this.cookieService.get('user');

    if (userCookie) {
      this.logStringResult = userCookie;
    } else {
      this.logStringResult = 'Login';
    }
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
        () => {
        },
        (error: HttpErrorResponse) => {
          console.log(error);
        }
      );
  }

}

