import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../Model/Product.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {


  constructor(private httpClient: HttpClient) { }



  getAllProducts(pageNumber: number, searchKeyword: string = ""): Observable<Product[]> {
    const url = `http://localhost:8080/getAllProducts`;
    return this.httpClient.get<Product[]>(url);
  }


  public getProductById(productId){
    return this.httpClient.get<Product>("http://localhost:8080/getProductById/"+productId);
  }

/*
  public deleteProduct(productId: number){
    return this.httpClient.delete("http://localhost:8080/deleteProductDetails/"+productId);
  }

  public getProductDetails(isSingeProductCheckout,productId){
    return this.httpClient.get<Product[]>("http://localhost:8080/getProductDetails/"+isSingeProductCheckout+"/"+productId);
  }

*/

}
