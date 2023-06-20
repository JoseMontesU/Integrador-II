import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DetallePedido } from '../interfaces/detalle-pedido.interface';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class DetallePedidoService {

  constructor(private http: HttpClient) { }

  getDetallePedidos(): Observable<DetallePedido[]>{
    const endpoint = `${ base_url }/pedido/detallepedido`;
    return this.http.get<DetallePedido[]>(endpoint);
  }

  getDetallePedidosByPedidoId(id: number): Observable<DetallePedido[]>{
    const endpoint = `${ base_url }/pedido/detallepedido/${id}`;
    return this.http.get<DetallePedido[]>(endpoint);
  }

  saveDetallePedido(body: DetallePedido){
    const endpoint = `${ base_url }/pedido/detallepedido`;
    return this.http.post<DetallePedido>(endpoint, body);
  }

  updateDetallePedido(body: DetallePedido, id: number){
    const endpoint = `${ base_url }/pedido/detallepedido/${id}`;
    return this.http.put<DetallePedido>(endpoint, body);
  }

  deleteDetallePedido(id: number){
    const endpoint = `${ base_url }/pedido/detallepedido/${id}`;
    return this.http.delete(endpoint);
  }

}
