import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../Service/product.service';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  ID: number | undefined;
  product: any = {};

  constructor(
    private productService: ProductService,
    private http: HttpClient,
    private route: ActivatedRoute
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
}
