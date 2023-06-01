import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  constructor(private http: HttpClient) { }

  uploadImage(imageFile: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      const formData: FormData = new FormData();
      formData.append('image', imageFile);

      this.http.post<any>('http://localhost:8080/getimg', formData).subscribe(
        (response) => {
          resolve(response.imageUrl);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }
}
