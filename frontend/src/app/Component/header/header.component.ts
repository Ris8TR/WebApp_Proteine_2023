import { Component } from '@angular/core';
import {ProductService} from "../../Service/product.service";
import {ImageProcessingService} from "../../Service/image-processing.service";
import {Router} from "@angular/router";
import {tap} from "rxjs/operators";
import {Product} from "../../Model/Product.model";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSidenav} from "@angular/material/sidenav";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  pageNumber: number = 0;
  productDetails = [[] as any];
  showLoadButton = false;

  constructor(private productService: ProductService,
              private router: Router){}



  ngOnInit(): void {
  }


  redirectToSearch(searchKeyword: string) {

    this.router.navigate(['/search', searchKeyword]);

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

