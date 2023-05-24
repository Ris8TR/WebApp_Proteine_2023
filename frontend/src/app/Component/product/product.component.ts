import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../Service/product.service';
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {CartService} from "../../Service/cart.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  ID: number | undefined;
  product: any = {};
  productAddedToCart: number | null = null;

  constructor(
    private productService: ProductService,
    private http: HttpClient,
    private cartService: CartService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.ID = Number(params.get('id'));
      this.getProductById();
    });
  }

  getProductById() {
    this.productService.getProductById(this.ID).subscribe(
      (data: any) => {
        this.product = data;
        console.log(this.product);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  goBack() {
    this.router.navigateByUrl('/home');
  }
  addToCart(ID){
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
  }
}
