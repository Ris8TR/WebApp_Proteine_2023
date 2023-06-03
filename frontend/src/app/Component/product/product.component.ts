import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../Service/product.service';
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {CartService} from "../../Service/cart.service";
import {FacebookService, InitParams} from "ngx-facebook";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})

export class ProductComponent implements OnInit {
  ID: number | undefined;
  product: any = {};
  productImageUrl: any;
  productAddedToCart: number | null = null;

  constructor(
    private productService: ProductService,
    private http: HttpClient,
    private cartService: CartService,
    private route: ActivatedRoute,
    private router: Router,
    private facebookService: FacebookService
  ) {}

  ngOnInit(): void {
    const initParams: InitParams = {
      appId: '560352242616863',
      xfbml: true,
      version: 'v2.8'
    };

    // Inizializza il servizio di Facebook dopo che la libreria è stata caricata correttamente
    window.onload = () => {
      this.facebookService.init(initParams);
    };

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
        this.setProductImageSrc(this.product.val_nutr);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  setProductImageSrc(base64Image: string): void {
    if (!base64Image) {
      this.productImageUrl = '/./assets/images/logo.png';
      return;
    }

    const imageBlob = this.base64ToBlob(base64Image);
    this.productImageUrl = URL.createObjectURL(imageBlob);
  }

  goBack() {
    this.router.navigateByUrl('/home');
  }

  addToCart(ID) {
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
  }

  getProductImageSrc(base64Image: string): string {
    if (!base64Image) {
      return '';
    }

    const imageBlob = this.base64ToBlob(base64Image);
    const imageUrl = URL.createObjectURL(imageBlob);

    setTimeout(() => {
      this.productImageUrl = imageUrl;
    });

    return this.productImageUrl;
  }

  base64ToBlob(base64Data: string): Blob {
    const byteString = atob(base64Data);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const uint8Array = new Uint8Array(arrayBuffer);

    for (let i = 0; i < byteString.length; i++) {
      uint8Array[i] = byteString.charCodeAt(i);
    }

    const blob = new Blob([arrayBuffer], { type: 'image/jpeg' });

    return blob;
  }

  protected readonly encodeURIComponent = encodeURIComponent;
}
