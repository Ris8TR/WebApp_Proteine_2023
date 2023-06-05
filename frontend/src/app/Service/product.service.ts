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

  public getProductByCategory(pageNumber: number, productCategory: string | undefined): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`http://localhost:8080/getProductByCategory?pageNumber=${pageNumber}&productCategory=${productCategory}`);
  }

  public getProductsBySearch(pageNumber: number, searchKeyword: string | undefined): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`http://localhost:8080/getProductsBySearch?pageNumber=${pageNumber}&searchKeyword=${searchKeyword}`);
  }

  public getProductById(productId){
    return this.httpClient.get<Product>("http://localhost:8080/getProductById/"+productId);
  }



}
