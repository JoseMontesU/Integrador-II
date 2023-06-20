import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../interfaces/user.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor( private http: HttpClient
  ) { }

    getUsers(): Observable<User[]> {
      const endpoint = `${ base_url }/user`;
      return this.http.get<User[]>(endpoint);
    }

}
