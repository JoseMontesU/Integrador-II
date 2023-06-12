import { Component, OnInit, ViewChild } from '@angular/core';
import { DateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { UtilService } from '../../shared/services/util.service';

export interface PeriodicElement {
  name: string;
  product: string;
  position: number;
  cantidad: number;
  fecha: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Ramon Perez', product: 'Arroz paisana 1kg', cantidad: 56, fecha: '2023-06-10'},
  {position: 2, name: 'Juan Matos', product: 'Arroz paisana 5kg', cantidad: 10, fecha: '2023-06-10'},
  {position: 3, name: 'Ramon Perez', product: 'Atún florida unid', cantidad: 40, fecha: '2023-06-10'},
  {position: 4, name: 'Juan Matos', product: 'Cerveza pilsen six pack', cantidad: 5, fecha: '2023-06-10'},
  {position: 5, name: 'Ana Ramos', product: 'Jabón bolivar unid', cantidad: 36, fecha: '2023-06-10'},
  {position: 6, name: 'Ana Ramos', product: 'Agua cielo 625ml', cantidad: 48, fecha: '2023-06-10'},
  {position: 7, name: 'Ana Ramos', product: 'Arroz paisana 1kg', cantidad: 49, fecha: '2023-06-10'},
  {position: 8, name: 'Ramon Perez', product: 'Leche gloria grande unid', cantidad: 24, fecha: '2023-06-10'},
  {position: 9, name: 'Juan Matos', product: 'Aceite primor vegetal 900ml', cantidad: 12, fecha: '2023-06-10'},
  {position: 10, name: 'Ramon Perez', product: 'Ricocan bolsa 22kg', cantidad: 10, fecha: '2023-06-10'},
];


@Component({
  selector: 'app-pedido',
  templateUrl: './pedido.component.html',
  styleUrls: ['./pedido.component.css']
})
export class PedidoComponent {

  displayedColumns: string[] = ['position', 'name', 'product', 'cantidad','fecha'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
