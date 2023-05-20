import {Component, OnInit} from '@angular/core';
import {Product} from "../../Model/Product.model";
import {ProductService} from "../../Service/product.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{
  products: any[]=[];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getAllProducts(1, "").subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
