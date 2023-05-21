import {Component, OnInit} from '@angular/core';
import {Product} from "../../Model/Product.model";
import {ProductService} from "../../Service/product.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{

  ID=0;
  product=[ [] as any];


  constructor(private productService: ProductService,
              private http: HttpClient,

              ) { }

  ngOnInit(): void {
    this.productService.getProductById(this.ID);
  }

  getProductById(id: number) {
    const url = `/getProductsById?id=${id}`; // Assicurati che l'URL corrisponda al tuo endpoint API
    return this.http.get<Product>(url);
  }

}
