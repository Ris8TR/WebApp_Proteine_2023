import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {OrderDetails} from "../Model/OrderDetails.model";
import {Product} from "../Model/Product.model";

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {


    constructor(private httpClient: HttpClient) { }

  public getAllOrderDetailsForAdmin() : Observable<OrderDetails[]>{
      return this.httpClient.get<OrderDetails[]>("http://localhost:8080/getAllOrderDetails");
    }

  public getMyOrders() : Observable<OrderDetails[]>{
      return this.httpClient.get<OrderDetails[]>("http://localhost:8080/getOrderDetails");
    }

  public deleteCartItem(cartId){
      return this.httpClient.delete("http://localhost:8080/deleteCartItem/"+cartId);
    }

  public addProduct(product: FormData){
      return this.httpClient.post<Product>("http://localhost:8080/addNewProduct", product);
    }

  public getAllProducts(pageNumber, searchKeyword: string= ""){
      return this.httpClient.get<Product[]>("http://localhost:8080/getAllProducts?pageNumber="+pageNumber+"&searchKey="+searchKeyword);
    }

  public getProductDetailsById(productId){
      return this.httpClient.get<Product>("http://localhost:8080/getProductDetailsById/"+productId);
    }

  public deleteProduct(productId: number){
      return this.httpClient.delete("http://localhost:8080/deleteProductDetails/"+productId);
    }



  public placeOrder(orderDetails: OrderDetails, isCartCheckout){
      return this.httpClient.post("http://localhost:8080/placeOrder/"+isCartCheckout, orderDetails);
    }

  public addToCart(productId){
      return this.httpClient.get("http://localhost:8080/addToCart/"+productId);
    }

  public getCartDetails(){
      return this.httpClient.get("http://localhost:8080/getCartDetails");
    }

}
