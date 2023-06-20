import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Pedido } from '../interfaces/pedido.interface';
import { Observable } from 'rxjs';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  constructor(private http: HttpClient) { }

  getPedidos() : Observable<Pedido[]> {
    const endpoint = `${ base_url }/pedido`;
    return this.http.get<Pedido[]>(endpoint);
  }

  savePedido(body: Pedido){
    const endpoint = `${ base_url }/pedido`;
    return this.http.post<Pedido>(endpoint, body);
  }

  updatePedido(body: Pedido, id: number){
    const endpoint = `${base_url}/pedido/${id}`;
    return this.http.put<Pedido>(endpoint, body);
  }
}
