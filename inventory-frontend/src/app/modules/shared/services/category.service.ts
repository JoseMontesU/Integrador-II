import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  httpOptions = { headers: new HttpHeaders({'Content-Type' : 'application/json',
  'Authorization': `Bearer ${sessionStorage.getItem('token')}`})};

  constructor(private http: HttpClient) { }

  getCategories(){

    const endpoint = `${base_url}/v1/categories`;
    return this.http.get(endpoint,this.httpOptions);

  }

  saveCategorie(body: any) {
    const endpoint = `${base_url}/v1/categories`;
    return this.http.post(endpoint, body,this.httpOptions);
  }

  updateCategorie(body: any, id: any){
    const endpoint = `${base_url}/v1/categories/ ${id}`;
    return this.http.put(endpoint, body, this.httpOptions);
  }

  deleteCategorie(id: any){
    const endpoint = `${base_url}/v1/categories/ ${id}`;
    return this.http.delete(endpoint, this.httpOptions);
  }

  getCategorieById(id: any){
    const endpoint = `${base_url}/v1/categories/ ${id}`;
    return this.http.get(endpoint, this.httpOptions);
  }

  getCategoryByName(name: any){
    const endpoint = `${ base_url}/v1/categories/filter/${name}`;
    return this.http.get(endpoint, this.httpOptions);
  }

  exportCategories(){
    const endpoint = `${base_url}/v1/categories/export/excel`;
    return this.http.get(endpoint, {
      responseType: 'blob'
    });
  }

}
