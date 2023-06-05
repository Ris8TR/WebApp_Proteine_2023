import {Component, Input} from '@angular/core';
import {Product} from "../../Model/Product.model";
import {HttpClient} from "@angular/common/http";
import {CartService} from "../../Service/cart.service";
import {CookieService} from "ngx-cookie-service";
import {ActivatedRoute, Router} from "@angular/router";
import {NavigationService} from "../../Service/navigation.service";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {


  productAddedToCart: number | null = null;


  constructor(private http: HttpClient,
              private cartService: CartService,
              private cookieService: CookieService,
              private router: Router,
              private route: ActivatedRoute,
              private navigationService: NavigationService) {
  }
  @Input() item: Product | undefined;




  showProductDetails(productId) {
    const currentRoute = this.router.url;
    this.navigationService.setPreviousComponent(currentRoute);
    this.router.navigate(['/product',  productId ]);
  }

  addToCart(ID) {
    this.cartService.addToCart(ID);
    this.productAddedToCart = ID;
    setTimeout(() => {
      this.productAddedToCart = null;
    }, 1000);
  }
}

