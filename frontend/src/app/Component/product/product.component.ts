import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../Service/product.service';
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {CartService} from "../../Service/cart.service";
import {FacebookService, InitParams} from "ngx-facebook";
import {NavigationService} from "../../Service/navigation.service";

declare global {
  interface Window {
    fbAsyncInit: any;
    FB: any;
  }
}

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})

export class ProductComponent implements OnInit {
  ID: number | undefined;
  product: any = {};
  productImageUrl: any;
  productValueUrl: any;
  productAddedToCart: number | null = null;

  constructor(
    private productService: ProductService,
    private http: HttpClient,
    private cartService: CartService,
    private route: ActivatedRoute,
    private router: Router,
    private facebookService: FacebookService,
    private navigationService: NavigationService
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
        this.setProductImageSrc(this.product.foto);
        this.setProductValSrc(this.product.val_nutr);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  setProductValSrc(base64Image: string): void {
    if (!base64Image) {
      this.productValueUrl = '/./assets/images/logo.png';
      return;
    }

    const imageBlob = this.base64ToBlob(base64Image);
    this.productValueUrl = URL.createObjectURL(imageBlob);
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
      this.navigationService.goBack();
  }

  addToCart(ID) {
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
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



