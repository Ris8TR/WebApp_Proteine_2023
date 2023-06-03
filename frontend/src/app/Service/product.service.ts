import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Product} from "../Model/Product.model";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class ProductService {


  constructor(private httpClient: HttpClient) { }



  public getAllProducts(pageNumber: number, searchKeyword: string = ""): Observable<Product[]> {
    return this.httpClient.get<Product[]>('http://localhost:8080/getAllProducts?pageNumber='+pageNumber);
  }



  public getProductById(productId){
    return this.httpClient.get<Product>("http://localhost:8080/getProductById/"+productId);
  }
  public getProductByCategory(productCategory){
    return this.httpClient.get<Product[]>("http://localhost:8080/getProductByCategory/"+productCategory);
  }

  public getProductsBySearch(Search){
    return this.httpClient.get<Product[]>("http://localhost:8080/getProductsBySearch/"+Search);
  }
/*
  public deleteProduct(productId: number){
    return this.httpClient.delete("http://localhost:8080/deleteProductDetails/"+productId);
  }

*/

}
